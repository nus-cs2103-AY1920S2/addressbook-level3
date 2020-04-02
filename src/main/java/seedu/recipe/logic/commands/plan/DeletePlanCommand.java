package seedu.recipe.logic.commands.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;

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

    public static final String MESSAGE_SUCCESS = "Planned recipe at Index %2$s is deleted:\n"
            + "%1$s\n";

    private final Index plannedIndex;
    private final Date atDate;
    private final Tab planTab = Tab.PLANNING;
    private final CommandType commandType;

    /**
     * Creates an DeletePlanCommand to delete the specified planned recipe on {@code date} at {@code index}.
     */
    public DeletePlanCommand(Index plannedIndex, Date date) {
        requireNonNull(plannedIndex);
        requireNonNull(date);
        this.plannedIndex = plannedIndex;
        this.commandType = CommandType.PLAN;
        this.atDate = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPlannedList(new PlannedRecipeOnDatePredicate(atDate));
        List<PlannedDate> plannedListAtDate = model.getFilteredPlannedList();

        if (plannedListAtDate.size() != 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLANNED_DATE);
        }

        PlannedDate plannedDateToEdit = plannedListAtDate.get(0);
        List<Recipe> recipesAtPlannedDate = plannedDateToEdit.getRecipes();
        if (plannedIndex.getOneBased() > recipesAtPlannedDate.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLANNED_RECIPE_DISPLAYED_INDEX);
        }

        Recipe recipeToDelete = recipesAtPlannedDate.get(plannedIndex.getZeroBased());
        model.deleteOnePlan(recipeToDelete, plannedDateToEdit);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);
        model.commitBook(commandType);

        return new CommandResult(String.format(MESSAGE_SUCCESS, recipeToDelete, plannedIndex.getOneBased()),
                false, planTab, false);
    }
}
