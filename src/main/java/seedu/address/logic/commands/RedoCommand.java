package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success: [%s]";
    public static final String MESSAGE_FAILURE = "Sorry! There's no Redoable Command!";

    @Override
    public final CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!undoRedoStack.canRedo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        CommandResult commandResult = undoRedoStack.popRedo().redo(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, commandResult.getFeedbackToUser()));
    }
}
