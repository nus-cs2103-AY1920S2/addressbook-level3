package tatracker.logic.commands;

import static tatracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tatracker.logic.commands.GotoCommand.SHOWING_GOTO_MESSAGE;

import org.junit.jupiter.api.Test;

import tatracker.model.Model;
import tatracker.model.ModelManager;

public class GotoCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_goto_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(SHOWING_GOTO_MESSAGE, "placeholder"),
                true, false, false);
        assertCommandSuccess(new GotoCommand("placeholder"), model, expectedCommandResult, expectedModel);
    }
}
