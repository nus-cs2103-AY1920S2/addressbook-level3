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
import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Date;
import seedu.recipe.model.Model;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.plan.PlannedRecipeOnDatePredicate;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Deletes planned recipes.
 */
public class DeletePlanCommand extends Command {

    public static final String COMMAND_WORD = "deleteplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a planned recipe on a certain day. "
            + "Parameters: " + "PLANNED_RECIPE_INDEX "
            + PREFIX_DATE + "YYYY-MM-DD \n"
            + "Example: " + COMMAND_WORD + " 3 "
            + PREFIX_DATE + "2020-03-16 ";

    public static final String MESSAGE_SUCCESS = "The following planned recipes have been deleted: \n";

    private final List<Index> indexes;
    private final Date atDate;
    private final Tab planTab = Tab.PLANNING;
    private final CommandType commandType;

    /**
     * Creates an DeletePlanCommand to delete the specified planned recipe on {@code date} at {@code index}.
     */
    public DeletePlanCommand(List<Index> indexes, Date date) {
        requireNonNull(indexes);
        requireNonNull(date);
        this.indexes = indexes;
        this.commandType = CommandType.PLAN;
        this.atDate = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPlannedList(new PlannedRecipeOnDatePredicate(atDate));
        List<PlannedDate> plansOnDate = model.getFilteredPlannedList();

        if (plansOnDate.size() != 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLANNED_DATE);
        }

        PlannedDate plannedDateToEdit = plansOnDate.get(0);
        List<Recipe> allPlannedRecipes = plannedDateToEdit.getRecipes();
        List<Recipe> recipesToDelete = parseIndexToRecipe(indexes, allPlannedRecipes);

        StringBuilder sb = new StringBuilder(MESSAGE_SUCCESS);
        sb.append("Date: " + plannedDateToEdit.getDate().toString() + "\nRecipe(es): ");

        for (int i = 0; i < recipesToDelete.size(); i ++) {
            Recipe recipeToDelete = recipesToDelete.get(i);
            model.deleteOnePlan(recipeToDelete, plannedDateToEdit);

            if (i != 0) {
                sb.append(", ");
            }
            sb.append(recipeToDelete.getName());
            plannedDateToEdit = model.getFilteredPlannedList().get(0); // update plans after deletion
        }

        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType);
        return new CommandResult(sb.toString(), false, false, planTab, false);
    }

    private List<Recipe> parseIndexToRecipe(List<Index> indexes, List<Recipe> allRecipes) throws CommandException {
        List<Recipe> recipesToDelete = new ArrayList<>();

        for (int i = 0; i < indexes.size(); i++) {
            Index currentIndex = indexes.get(i);
            if (currentIndex.getOneBased() > allRecipes.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PLANNED_RECIPE_DISPLAYED_INDEX);
            }

            recipesToDelete.add(allRecipes.get(currentIndex.getZeroBased()));
        }

        return recipesToDelete;
    }
}
