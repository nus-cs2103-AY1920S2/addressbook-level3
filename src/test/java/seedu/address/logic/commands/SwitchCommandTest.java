
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SwitchCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.ui.tab.Tab;

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
