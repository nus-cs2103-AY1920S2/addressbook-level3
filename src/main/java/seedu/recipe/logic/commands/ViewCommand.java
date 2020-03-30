package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;

import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.plan.PlannedRecipeWithinDateRangePredicate;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.plan.PlannedRecipe;
import seedu.recipe.model.recipe.Recipe;

/**
 * Schedules a recipe to a date.
 */

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the planed recipes in the specified format.";

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
        model.commitRecipeBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, type));
    }

}
