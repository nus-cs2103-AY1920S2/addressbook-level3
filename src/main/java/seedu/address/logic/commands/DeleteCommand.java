package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "(ab)delete";
    public static final String COMMAND_FUNCTION = "Deletes the person identified by the "
            + "index number used in the displayed person list if no fields are specified. "
            + "If fields are specified, the field(s) for the person identified by the index "
            + "will be deleted.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EMAIL + "] "
            + "[" + PREFIX_ADDRESS + "] "
            + "[" + PREFIX_BIRTHDAY + "] "
            + "[" + PREFIX_REMARK + "] "
            + "[" + PREFIX_ORGANIZATION + "] "
            + "[" + PREFIX_DELETE_TAG + "]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ADDRESS + " " + PREFIX_EMAIL;

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_FIELD_SUCCESS = "Deleted field(s).";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private final EditPersonDescriptor editPersonDescriptor;

    public DeleteCommand(Index targetIndex, EditPersonDescriptor editPersonDescriptor) {
        this.targetIndex = targetIndex;
        this.editPersonDescriptor = editPersonDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        } else {
            Person editedPerson = editPersonDescriptor.createEditedPerson(personToDelete);

            if (!personToDelete.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            model.setPerson(personToDelete, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELETE_FIELD_SUCCESS, personToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
