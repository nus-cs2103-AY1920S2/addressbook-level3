package seedu.recipe.logic.commands.plan;

import static java.util.Objects.requireNonNull;

import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.CommandType;
import seedu.recipe.model.Model;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.ui.tab.Tab;

/**
 * Deletes all planned recipes.
 */
public class ClearPlanCommand extends Command {
    public static final String COMMAND_WORD = "clearplan";
    public static final String MESSAGE_SUCCESS = "Planned recipes have been cleared!";
    private final Tab planTab = Tab.PLANNING;
    private final CommandType commandType = CommandType.PLAN;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setPlannedBook(new PlannedBook());
        model.commitBook(commandType);
        return new CommandResult(MESSAGE_SUCCESS, false, planTab, false);
    }
}
