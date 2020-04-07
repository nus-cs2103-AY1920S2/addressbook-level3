package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

/**
 * Edit a remark of an existing person in the address book.
 */
public class EditInfoCommand extends Command {

    public static final String COMMAND_WORD = "(ab)editnote";
    public static final String COMMAND_FUNCTION = "Edit the information of the person identified "
            + "by the index number used in the last person listing. "
            + "If there is existing information at the line number, "
            + "the input will added on to the existing information.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX and LINE_NUMBER (must be a positive integer) "
            + PREFIX_LINE_NUMBER + "LINE_NUMBER " + PREFIX_REMARK + "INFO\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINE_NUMBER + " 2 " + PREFIX_REMARK + " Likes to swim.";

    public static final String MESSAGE_EDIT_REMARK_SUCCESS = "Edited remark for Person: %1$s";
    public static final String MESSAGE_EMPTY = "No remark to be edited is provided.";

    private final Index index;
    private final int line;
    private final Remark remark;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param line number of a specific note in the information stored
     * @param remark of the person to be updated to
     */
    public EditInfoCommand(Index index, int line, Remark remark) {
        requireAllNonNull(index, line, remark);

        this.index = index;
        this.line = line;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (personToEdit.getRemark().size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_INFO);
        }
        if (line > personToEdit.getRemark().size() && personToEdit.getRemark().size() != 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_LINE_NUMBER);
        }
        if (remark.value.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY);
        }
        personToEdit.getRemark().set(line - 1, remark);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getBirthday(),
                personToEdit.getOrganization(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_EDIT_REMARK_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditInfoCommand)) {
            return false;
        }

        // state check
        EditInfoCommand e = (EditInfoCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark)
                    && line == e.line;
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
