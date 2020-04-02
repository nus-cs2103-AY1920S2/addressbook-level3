package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static csdev.couponstash.logic.commands.GoToCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;

public class GoToCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_validYearMonth_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS, "8-2020"));
        assertCommandSuccess(new GoToCommand("8-2020"), model, expectedCommandResult, expectedModel);
    }
}
