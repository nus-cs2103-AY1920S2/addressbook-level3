package seedu.foodiebot.logic.commands;

import java.io.IOException;

import seedu.foodiebot.logic.commands.exceptions.CommandException;
import seedu.foodiebot.model.Model;

/** Represents a command with hidden internal logic and the ability to be executed. */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    public boolean needToSaveCommand() {
        return true;
    }
    public abstract CommandResult execute(Model model) throws CommandException, IOException;

}
