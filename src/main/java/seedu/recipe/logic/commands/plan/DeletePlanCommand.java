package seedu.recipe.logic.commands.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
 * Deletes planned recipe(s).
 */
public class DeletePlanCommand extends Command {

    public static final String COMMAND_WORD = "deleteplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a planned recipe on a certain day. "
            + "Parameters: " + "PLANNED_RECIPE_INDEX "
            + PREFIX_DATE + "YYYY-MM-DD \n"
            + "Example: " + COMMAND_WORD + " 3 "
            + PREFIX_DATE + "2020-03-16 ";

    public static final String MESSAGE_SUCCESS = "Date: %1$s \n"
            + "The plans at the following index(es) have been deleted:\n"
            + "%2$s";
    public static final String MESSAGE_INVALID_DATE = "There are no plans on %1$s.";

    private final Index[] indexes;
    private final Date atDate;
    private final Tab planTab = Tab.PLANNING;
    private final CommandType commandType;
    private List<String> deletedPlansMessage;

    /**
     * Creates an DeletePlanCommand to delete the specified planned recipe on {@code date} at {@code index}.
     */
    public DeletePlanCommand(Index[] indexes, Date date) {
        requireNonNull(indexes);
        requireNonNull(date);
        this.indexes = indexes;
        this.commandType = CommandType.PLAN;
        this.atDate = date;
        deletedPlansMessage = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        PlannedDate plannedDateToEdit = getCurrentPlansOnDate(atDate, model);

        List<Recipe> allPlannedRecipes = plannedDateToEdit.getRecipes();
        List<Recipe> recipesToDelete = parseIndexToRecipe(indexes, allPlannedRecipes);

        for (int i = 0; i < recipesToDelete.size(); i ++) {
            plannedDateToEdit = getCurrentPlansOnDate(atDate, model); // update plans after deletion

            Recipe recipeToDelete = recipesToDelete.get(i);
            model.deleteOnePlan(recipeToDelete, plannedDateToEdit);
        }

        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType);
        return new CommandResult(formatSuccessMessage(deletedPlansMessage, atDate), false,
                false, planTab, false);
    }

    /**
     * Converts the indexes of the planned recipes to recipes.
     */
    private List<Recipe> parseIndexToRecipe(Index[] indexes, List<Recipe> allRecipes) throws CommandException {
        List<Recipe> recipesToDelete = new ArrayList<>();

        for (int i = 0; i < indexes.length; i++) {
            Index currentIndex = indexes[i];
            if (currentIndex.getOneBased() > allRecipes.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PLANNED_RECIPE_DISPLAYED_INDEX);
            }
            Recipe recipe = allRecipes.get(currentIndex.getZeroBased());
            deletedPlansMessage.add(formatIndexToString(currentIndex, recipe));
            recipesToDelete.add(recipe);
        }
        return recipesToDelete;
    }

    /**
     * Returns the current plans on a date.
     */
    private static PlannedDate getCurrentPlansOnDate(Date date, Model model) throws CommandException {
        model.updateFilteredPlannedList(new PlannedRecipeOnDatePredicate(date));
        List<PlannedDate> plannedDates = model.getFilteredPlannedList();

        if (plannedDates.size() != 1) {
            throw new CommandException(String.format(MESSAGE_INVALID_DATE, date));
        }

        return plannedDates.get(0);
    }

    /**
     * Formats the {@code index} and {@code recipe} into the format [Index (Recipe Name)].
     */
    private static String formatIndexToString(Index index, Recipe recipe) {
        return index.getOneBased() + " (" + recipe.getName() +")";
    }

    /**
     * Formats the success message of this command.
     */
    private static String formatSuccessMessage(List<String> deletedPlans, Date date) {
        String formattedRecipes = deletedPlans.stream().collect(Collectors.joining(", "));
        return String.format(MESSAGE_SUCCESS, date, formattedRecipes);
    }

}
