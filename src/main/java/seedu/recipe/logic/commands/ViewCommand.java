package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.plan.PlannedRecipeWithinDateRangePredicate;

/**
 * Schedules a recipe to a date.
 */

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the planned recipes in the specified format.";

    public static final String MESSAGE_SUCCESS = "View format changed to %1$s";

    private final PlannedRecipeWithinDateRangePredicate predicate;

    private final String type = "week"; // change to enum types?

    /**
     * Creates an ViewCommand to set the view of the planned recipes.
     */
    public ViewCommand(PlannedRecipeWithinDateRangePredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPlannedList(predicate);
        // model.commitBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, type));
    }

}
