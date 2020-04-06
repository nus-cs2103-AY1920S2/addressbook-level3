package seedu.recipe.logic.commands;

import seedu.recipe.model.Model;

/**
 * Represents a quote command.
 */
public class StreakCommand extends Command {

    public static final String COMMAND_WORD = "streak";

    public static final String MESSAGE_SUCCESS = "Displays current streak";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
