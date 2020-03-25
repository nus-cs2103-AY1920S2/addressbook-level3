package seedu.recipe.logic.commands;

import seedu.recipe.model.Model;

/**
 * Lists all goals being tracked.
 */
public class ListGoalsCommand extends Command {

    public static final String COMMAND_WORD = "listgoals";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": lists all available goals to be tracked";
    public static final String LIST = "Bulk like the Hulk\n"
            + "Wholesome Wholegrains\n"
            + "Herbivore";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(LIST);
    }
}
