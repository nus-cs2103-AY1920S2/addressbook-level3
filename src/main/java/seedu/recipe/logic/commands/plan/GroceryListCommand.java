package seedu.recipe.logic.commands.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;

import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.ui.tab.Tab;

/**
 * Collates and lists all the ingredients used in the planned recipes.
 */
public class GroceryListCommand extends Command {

    public static final String COMMAND_WORD = "grocerylist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all ingredients needed for the planned recipes.";

    public static final String MESSAGE_EMPTY_LIST = "There are no planned recipes. Why not plan one today?";

    public static final String MESSAGE_SUCCESS = "All ingredients required have been listed down.";

    private final Tab planTab = Tab.PLANNING;
    //private final CommandType commandType = CommandType.PLAN;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPlannedList(PREDICATE_SHOW_ALL_PLANNED_RECIPES);

        if (model.getFilteredPlannedList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        return new CommandResult(MESSAGE_SUCCESS, true, false, planTab, false);
    }
}
