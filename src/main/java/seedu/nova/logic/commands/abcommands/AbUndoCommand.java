package seedu.nova.logic.commands.abcommands;

import static java.util.Objects.requireNonNull;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;

/**
 * Clears the nova book.
 */
public class AbUndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Address book has been undo-ed!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the address book\n"
            + "Example: undo";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.canUndoAddressBook() == false) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
