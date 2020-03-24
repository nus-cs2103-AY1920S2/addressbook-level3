package csdev.couponstash.logic.commands;

import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param commandText the command that triggered this execution
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, String commandText) throws CommandException;

    /**
     * Executes the command and returns the result message, used for tests that do not require
     * a commandText
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public CommandResult execute(Model model) throws CommandException {
        return execute(model, "");
    }

}
