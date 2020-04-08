package seedu.recipe.logic.commands.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;

import java.util.ArrayList;
import java.util.Arrays;
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
import seedu.recipe.model.plan.DuplicatePlannedRecipeException;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Plans recipe(s) on a date.
 */
public class PlanCommand extends Command {

    public static final String COMMAND_WORD = "plan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Plans a recipe to be cooked in the future. "
            + "Parameters: "
            + "RECIPE_INDEX "
            + PREFIX_DATE + "YYYY-MM-DD\n"
            + "Example: " + COMMAND_WORD + " 3 "
            + PREFIX_DATE + "2020-03-16";

    public static final String MESSAGE_INVALID_DATE = "The latest date you can input is today's date.";
    public static final String MESSAGE_DATE = "Date: %1$s\n";
    public static final String MESSAGE_SUCCESS = "The recipe(s) at the following index(es) have been successfully " +
            "planned:\n%1$s";
    public static final String MESSAGE_DUPLICATE_PLANNED_RECIPE = "The recipe(s) at the following index(es) have " +
            "already been planned on this date:\n%1$s";

    private final Index[] indexes;
    private final Tab planTab = Tab.PLANNING;
    private final Date atDate;
    private final CommandType commandType;

    /**
     * Creates an PlanCommand to set the specified {@code Recipe} on a certain date
     */
    public PlanCommand(Index[] indexes, Date date) {
        requireNonNull(indexes);
        requireNonNull(date);
        this.indexes = indexes;
        this.commandType = CommandType.PLAN;
        this.atDate = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredRecipeList();
        List<String> newPlansMessage = new ArrayList<>();
        List<String> duplicatePlansMessage = new ArrayList<>();

        if (!allIndexesAreValid(indexes, lastShownList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        for (int i = 0; i < indexes.length; i++) {
            Index currentIndex = indexes[i];
            Recipe recipeToPlan = lastShownList.get(currentIndex.getZeroBased());

            List<Recipe> recipesToPlan = new ArrayList<>();
            recipesToPlan.add(recipeToPlan);
            PlannedDate plannedDate = new PlannedDate(recipesToPlan, atDate);

            try {
                model.addOnePlan(recipeToPlan, plannedDate);
            } catch (DuplicatePlannedRecipeException dp) {
                duplicatePlansMessage.add(formatIndexToString(currentIndex, recipeToPlan));
                continue;
            }
            newPlansMessage.add(formatIndexToString(currentIndex, recipeToPlan));
        }

        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType);
        return new CommandResult(formatSuccessMessage(newPlansMessage, duplicatePlansMessage, atDate),
                false, false, planTab, false);
    }

    /**
     * Returns true if all {@code indexes} are within the size of the planned {@code recipes} list.
     */
    private static boolean allIndexesAreValid(Index[] indexes, List<Recipe> recipes) {
        List<Index> invalidIndexes = Arrays.stream(indexes)
                .filter(index -> index.getOneBased() > recipes.size())
                .collect(Collectors.toList());
        return invalidIndexes.isEmpty();
    }

    /**
     * Formats the {@code index} and {@code recipe} into the format [Index (Recipe Name)].
     */
    private static String formatIndexToString(Index index, Recipe recipe) {
        return index.getOneBased() + " (" + recipe.getName() +")";
    }

    /**
     * Concatenates a list of {@code strings} with ','.
     */
    private static String formatListToString(List<String> strings) {
        return strings.stream().collect(Collectors.joining(", "));
    }

    /**
     * Formats the success message of this command.
     */
    private static String formatSuccessMessage(List<String> newPlans, List<String> duplicatePlans, Date date) {
        StringBuilder sb = new StringBuilder(String.format(MESSAGE_DATE, date));
        if (!newPlans.isEmpty()) {
            sb.append(String.format(MESSAGE_SUCCESS, formatListToString(newPlans)));
            sb.append("\n");
        }

        if (!duplicatePlans.isEmpty()) {
            sb.append(String.format(MESSAGE_DUPLICATE_PLANNED_RECIPE, formatListToString(duplicatePlans)));
        }
        return sb.toString();
    }
}
