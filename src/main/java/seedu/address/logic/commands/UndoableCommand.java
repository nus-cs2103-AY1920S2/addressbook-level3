package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public abstract class UndoableCommand extends Command {
    protected Command oppositeCommand;

    protected abstract CommandResult executeUndoableCommand(Model model) throws CommandException;

    /**
     * If require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand(Model model) throws CommandException {
    }

    protected final CommandResult undo(Model model) {
        requireNonNull(model);
        try {
            CommandResult commandResult = oppositeCommand.execute(model);
            return commandResult;
        } catch (Exception ce) {
            throw new AssertionError("This command should not fail.");
        }
    }

    protected abstract void generateOppositeCommand() throws CommandException;

    protected final CommandResult redo(Model model) {
        requireNonNull(model);
        try {
            CommandResult commandResult = executeUndoableCommand(model);
            return commandResult;
        } catch (Exception ce) {
            throw new AssertionError("The command has been successfully executed previously");
        }
    }

    @Override
    public final CommandResult execute(Model model) throws CommandException {
        preprocessUndoableCommand(model);
        generateOppositeCommand();
        return executeUndoableCommand(model);
    }
}
