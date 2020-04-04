package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a remark of an existing person in the address book.
 */
public class DeleteInfoCommand extends Command {

    public static final String COMMAND_WORD = "(ab)deletenote";
    public static final String COMMAND_FUNCTION = "Delete the information of the person identified "
            + "by the index number used in the last person listing. "
            + "If there is existing information at the line number "
            + "it will be deleted.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_LINE_NUMBER + "LINE_NUMBER] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINE_NUMBER + " 2 ";

    public static final String MESSAGE_REMOVE_REMARK_SUCCESS = "Deleted remark for Person: %1$s";
    public static final String MESSAGE_NO_DELETE_REMARK = "No remark removed from Person: %1$s";

    private final Index index;
    private final int line;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param line number of a specific note in the information stored
     */
    public DeleteInfoCommand(Index index, int line) {
        requireAllNonNull(index, line);

        this.index = index;
        this.line = line;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (personToEdit.getRemark().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_INFO);
        }

        if (line > personToEdit.getRemark().size() || line < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_LINE_NUMBER);
        } else {
            personToEdit.getRemark().remove(line - 1);
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getBirthday(),
                personToEdit.getOrganization(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the remark is edited to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = (line > personToEdit.getRemark().size() || line < 0)
                ? MESSAGE_REMOVE_REMARK_SUCCESS : MESSAGE_NO_DELETE_REMARK;
        return String.format(message, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteInfoCommand)) {
            return false;
        }

        // state check
        DeleteInfoCommand e = (DeleteInfoCommand) other;
        return index.equals(e.index)
                && line == e.line;
    }
}
