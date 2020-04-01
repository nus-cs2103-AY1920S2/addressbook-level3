package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "undo success";
    public static final String MESSAGE_FAILURE = "Sorry! There's no Undoable Command!";

    @Override
    public final CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!undoRedoStack.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        undoRedoStack.popUndo().undo(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
