package fithelper.logic.commands;

import fithelper.logic.exceptions.CommandException;
import fithelper.model.Model;

/**
 * Reverts the {@code model}'s baking home to its previously undone state.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    private static final String MESSAGE_REDO_SUCCESS = "Redo: %s";
    private static final String MESSAGE_REDO_STACK_EMPTY = "No more tasks to be redone.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canRedo()) {
            throw new CommandException(MESSAGE_REDO_STACK_EMPTY);
        }

        return new CommandResult(String.format(MESSAGE_REDO_SUCCESS, model.redo()));
    }
}
