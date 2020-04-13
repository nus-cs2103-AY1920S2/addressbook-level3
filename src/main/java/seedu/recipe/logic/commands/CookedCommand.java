package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.recipe.commons.core.Messages;
import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Date;
import seedu.recipe.model.Model;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Adds cooked recipes identified by index into cookedRecordsBook.
 */
public class CookedCommand extends Command {
    public static final String COMMAND_WORD = "cooked";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Indicate that the recipe has been cooked at the current time\n"
            + "Parameters: [recipe index]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DUPLICATE_RECORD = "This recipe has already been added!";
    public static final String MESSAGE_SUCCESS_COOK = "Cooked %1$s!";
    public static final String MESSAGE_SUCCESS_PLAN = "The plans for %1$s have been removed.";

    private final Index[] targetIndex;
    private final Tab goalsTab = Tab.GOALS;
    private final CommandType commandType;

    public CookedCommand(Index[] targetIndex) {
        this.targetIndex = targetIndex;
        this.commandType = CommandType.GOALS;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recipe> mostRecentList = model.getFilteredRecipeList();
        List<String> cookedMealsMessage = new ArrayList<>(); // lists to hold messages to user
        List<String> removedPlansMessage = new ArrayList<>();

        for (int i = 0; i < targetIndex.length; i++) {
            if (targetIndex[i].getZeroBased() >= mostRecentList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
            }
            Recipe recipeCooked = mostRecentList.get(targetIndex[i].getZeroBased());
            Record record = new Record(recipeCooked.getName(), new Date(), recipeCooked.getGoals());
            if (model.hasRecord(record)) {
                throw new CommandException(MESSAGE_DUPLICATE_RECORD);
            }
            //add record to internal list and update goals tally for each record added
            model.addRecord(record);
            model.updateGoalsTally(record);
            cookedMealsMessage.add(record.getName().toString());
            removePlanIfPresent(recipeCooked, model, removedPlansMessage);
        }
        model.commitBook(commandType, goalsTab);
        return new CommandResult(formatSuccessMessage(cookedMealsMessage, removedPlansMessage),
                false, false, goalsTab, false);
    }

    /**
     * Removes the plan from {@code model} if a plan for {@code recipeCooked} is present today.
     * If successful, adds the recipe name to {@code message}.
     */
    private static void removePlanIfPresent(Recipe recipeCooked, Model model, List<String> message) {
        Optional<List<Plan>> optionalPlans = model.getPlans(recipeCooked);
        if (optionalPlans.isPresent()) {
            optionalPlans.get().stream()
                    .filter(plan -> plan.isOnDate(Date.today()))
                    .findFirst()
                    .ifPresent(planToday -> {
                        model.deletePlan(recipeCooked, planToday);
                        message.add(recipeCooked.getName().toString());
                    });
        }
    }

    /**
     * Formats the success message of the cooked command.
     */
    private static String formatSuccessMessage(List<String> cookedMeals, List<String> removedPlans) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_SUCCESS_COOK, formatListToString(cookedMeals)));

        if (!removedPlans.isEmpty()) {
            sb.append("\n");
            sb.append(String.format(MESSAGE_SUCCESS_PLAN, formatListToString(removedPlans)));
        }
        return sb.toString();
    }

    /**
     * Concatenates a list of {@code strings} with ','.
     */
    private static String formatListToString(List<String> strings) {
        return strings.stream().collect(Collectors.joining(", "));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookedCommand // instanceof handles nulls
                && Arrays.equals(targetIndex, ((CookedCommand) other).targetIndex)); // state check
    }

}
