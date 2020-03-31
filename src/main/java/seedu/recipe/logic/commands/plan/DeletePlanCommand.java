package seedu.recipe.logic.commands.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;

import java.util.ArrayList;
import java.util.List;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.plan.PlannedRecipe;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Deletes planned recipes.
 */
public class DeletePlanCommand extends Command {

    public static final String COMMAND_WORD = "deleteplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a planned recipe on a certain day. "
            + "Parameters: "
            + PREFIX_DATE + "YYYY-MM-DD \n"
            + "PLANNED_RECIPE_INDEX "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-03-16 "
            + "3";

    public static final String MESSAGE_SUCCESS = "Planned recipe %1$s at %2$s is deleted";

    private final Index plannedIndex;
    private final PlannedDate atDate;
    private final Tab planTab = Tab.PLANNING;

    /**
     * Creates an DeletePlanCommand to delete the specified planned recipe on {@code date} at {@code index}.
     */
    public DeletePlanCommand(Index plannedIndex, PlannedDate date) {
        requireNonNull(plannedIndex);
        requireNonNull(date);
        this.plannedIndex = plannedIndex;
        atDate = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);



        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (plannedIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        Recipe recipeToPlan = lastShownList.get(plannedIndex.getZeroBased());
        List<Recipe> recipesToPlan = new ArrayList<>();
        recipesToPlan.add(recipeToPlan);

        PlannedRecipe plannedRecipe = new PlannedRecipe(recipesToPlan, atDate);

        model.addPlannedRecipe(plannedRecipe);
        model.addPlannedMapping(recipeToPlan, plannedRecipe);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitRecipeBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, recipeToPlan.toString(), atDate.toString()),
                false, planTab, false);
    }
}
