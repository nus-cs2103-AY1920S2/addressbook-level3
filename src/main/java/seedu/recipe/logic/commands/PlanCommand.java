package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.plan.PlannedRecipe;
import seedu.recipe.model.recipe.Recipe;

/**
 * Schedules a recipe to a date.
 */

public class PlanCommand extends Command {

    public static final String COMMAND_WORD = "plan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Plans a recipe to be cooked on a certain date. "
            + "Parameters: "
            + "RECIPE_INDEX "
            + PREFIX_DATE + "YYYY-MM-DD \n"
            + "Example: " + COMMAND_WORD + " "
            + "3 "
            + PREFIX_DATE + "2020-03-16";

    public static final String MESSAGE_SUCCESS = "Recipe planned: %1$s, %2$s";

    private final Index index;
    private final PlannedDate atDate;

    /**
     * Creates an PlanCommand to set the specified {@code Recipe} on a certain date
     */
    public PlanCommand(Index index, PlannedDate date) {
        requireNonNull(index);
        requireNonNull(date);
        this.index = index;
        atDate = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        Recipe recipeToPlan = lastShownList.get(index.getZeroBased());

        PlannedRecipe plannedRecipe = new PlannedRecipe(recipeToPlan, atDate);

        model.addPlannedRecipe(plannedRecipe);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitRecipeBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, recipeToPlan.toString(), atDate.toString()));
    }

}
