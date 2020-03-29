package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "redo success";
    public static final String MESSAGE_FAILURE = "redo failure, no more commands";

    @Override
    public final CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!undoRedoStack.canRedo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        undoRedoStack.popRedo().redo(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
