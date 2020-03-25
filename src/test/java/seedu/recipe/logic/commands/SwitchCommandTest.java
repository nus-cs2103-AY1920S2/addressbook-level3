
package seedu.recipe.logic.commands;

import static seedu.recipe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recipe.logic.commands.SwitchCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.ui.tab.Tab;

class SwitchCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_viewPlanning_success() {
        CommandResult expectedCommandResult =
                new CommandResult(MESSAGE_SUCCESS, false, Tab.PLANNING, false);
        assertCommandSuccess(new SwitchCommand(Tab.PLANNING), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_viewRecipes_success() {
        CommandResult expectedCommandResult =
                new CommandResult(MESSAGE_SUCCESS, false, Tab.RECIPES, false);
        assertCommandSuccess(new SwitchCommand(Tab.RECIPES), model, expectedCommandResult, expectedModel);
    }
}
