package seedu.recipe.logic.commands.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_DATE;

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
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.ui.tab.Tab;

/**
 * Deletes plans from the planned list.
 */
public class DeletePlanCommand extends Command {

    public static final String COMMAND_WORD = "deleteplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes planned recipe(s) on a certain date. \n"
            + "Parameters: " + "[planned recipe index]...\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 "
            + PREFIX_DATE + "2020-03-16 \n"
            + "Deletes planned recipes at indexes 1, 2 and 3 ";

    public static final String MESSAGE_SUCCESS = "The plans at the following index(es) have been deleted:\n%1$s";

    private final Index[] indexes;
    private final Tab planTab = Tab.PLANNING;
    private final CommandType commandType;

    /**
     * Creates an DeletePlanCommand to deletePlan the specified planned recipe on {@code date} at {@code index}.
     */
    public DeletePlanCommand(Index[] indexes) {
        requireNonNull(indexes);
        this.indexes = indexes;
        this.commandType = CommandType.PLAN;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Plan> lastShownPlannedList = model.getFilteredPlannedList();
        if (!allIndexesAreValid(indexes, lastShownPlannedList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        List<Plan> plansToDelete = convertIndexesToRecipes(indexes, lastShownPlannedList);
        List<String> deletedPlansMessage = new ArrayList<>();

        for (int i = 0; i < plansToDelete.size(); i++) {
            Plan plan = plansToDelete.get(i);
            Recipe recipe = plan.getRecipe();
            model.deletePlan(recipe, plan);
            deletedPlansMessage.add(formatIndexToString(indexes[i], recipe, plan.getDate()));
        }

        model.commitBook(commandType, planTab);
        return new CommandResult(formatSuccessMessage(deletedPlansMessage), false,
                false, planTab, false);
    }


    /**
     * Returns true if all {@code indexes} are within the size of the {@code list}.
     */
    private static boolean allIndexesAreValid(Index[] indexes, List<?> list) {
        List<Index> invalidIndexes = Arrays.stream(indexes)
                .filter(index -> index.getOneBased() > list.size())
                .collect(Collectors.toList());
        return invalidIndexes.isEmpty();
    }

    /**
     * Converts {@code indexes} to their respective Plan in the {@code plans} list.
     */
    private static List<Plan> convertIndexesToRecipes(Index[] indexes, List<Plan> plans) {
        return Arrays.stream(indexes)
                .map(index -> plans.get(index.getZeroBased()))
                .collect(Collectors.toList());
    }

    /**
     * Formats the {@code index} and {@code recipe} into the format [index (recipe name on date)] for printing.
     */
    private static String formatIndexToString(Index index, Recipe recipe, Date date) {
        return index.getOneBased() + " (" + recipe.getName() + " on " + date + ")";
    }

    /**
     * Formats the success message of this command.
     */
    private static String formatSuccessMessage(List<String> deletedPlans) {
        String formattedRecipes = deletedPlans.stream().collect(Collectors.joining(", "));
        return String.format(MESSAGE_SUCCESS, formattedRecipes);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePlanCommand // instanceof handles nulls
                && Arrays.equals(indexes, ((DeletePlanCommand) other).indexes)); // state check
    }

}
