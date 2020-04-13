package seedu.nova.logic.commands.abcommands;

import static seedu.nova.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.List;

import seedu.nova.commons.core.Messages;
import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;
import seedu.nova.model.person.Person;
import seedu.nova.model.person.Remark;


/**
 * Changes the remark of an existing person in the address book.
 */
public class AbRemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: " + PREFIX_INDEX + "[index] "
            + PREFIX_REMARK + "[remark]\n"
            + "Example: " + COMMAND_WORD + " i\\1 "
            + PREFIX_REMARK + "Likes to swim.\n"
            + "Note: Please only use remark command after using list, list c\\classmate, list c\\teammate or "
            + "find command. You may wish to undo if you accidentally used remark command on the wrong person.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s.";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s.\n\n"
            + "Note: Please only use remark command after using list, list c\\classmate, list c\\teammate or "
            + "find command. You may wish to undo if you accidentally used remark command on the wrong person.";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param remark of the person to be updated to
     */
    public AbRemarkCommand(Index index, Remark remark) {
        requireAllNonNull(index, remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getCategory(), remark);

        model.setPerson(personToEdit, editedPerson);
        //model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        //String message = !remark.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        String message = "";
        if (!remark.value.isEmpty()) {
            message = MESSAGE_ADD_REMARK_SUCCESS;
            message = message + "\nRemark: " + remark + "\n\n"
                + "Note: Please only use remark command after using list, list c\\classmate, list c\\teammate or "
                + "find command. You may wish to undo if you accidentally used remark command on the wrong person.";
        } else {
            message = MESSAGE_DELETE_REMARK_SUCCESS;
        }

        return String.format(message, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AbRemarkCommand)) {
            return false;
        }

        // state check
        AbRemarkCommand e = (AbRemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
