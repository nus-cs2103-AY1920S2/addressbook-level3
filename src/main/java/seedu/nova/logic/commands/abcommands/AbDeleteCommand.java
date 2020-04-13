package seedu.nova.logic.commands.abcommands;

import static java.util.Objects.requireNonNull;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.nova.commons.core.Messages;
import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;
import seedu.nova.model.person.Person;


/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class AbDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: " + PREFIX_INDEX + "[index] \n"
            + "Example: " + COMMAND_WORD + " i\\1\n"
            + "Note: Please only use delete command after using list, list c\\classmate, list c\\teammate or "
            + "find command. You may wish to undo if you accidentally used delete command on the wrong person.";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s\n\n"
            + "Note: Please only use delete command after using list, list c\\classmate, list c\\teammate or "
            + "find command. You may wish to undo if you accidentally used delete command on the wrong person.";

    private final Index targetIndex;

    public AbDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AbDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((AbDeleteCommand) other).targetIndex)); // state check
    }
}
