package seedu.recipe.logic.commands;

import seedu.recipe.model.Model;

/**
 * Represents a quote command.
 */
public class QuoteCommand extends Command {

    public static final String COMMAND_WORD = "quote";

    public static final String MESSAGE_SUCCESS = "Displays an inspiration quote";

    public static final String LIST = "The groundwork of all happiness is health.\n"
            + "It took more than a day to put it on. It will take more than a day to take it off.\n"
            + "If you keep good food in your fridge, you will eat good food.\n"
            + "One must eat to live, not live to eat.\n"
            + "Don’t dig your grave with your own knife and fork.\n"
            + "Your goals, minus your doubts, equal your reality.\n";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
