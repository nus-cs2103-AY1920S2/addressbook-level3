package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.View;

public class ViewCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_calendar_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(ViewCommand.MESSAGE_SUCCESS, "calendar"),
                false, false,
                false, true, false, false, CommandResult.EMPTY_BYTE_ARRAY_DATA);
        assertCommandSuccess(new ViewCommand(View.CALENDAR), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_modules_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(ViewCommand.MESSAGE_SUCCESS, "modules"),
                false, false,
                false, true, false, false, CommandResult.EMPTY_BYTE_ARRAY_DATA);
        assertCommandSuccess(new ViewCommand(View.MODULES), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_statistics_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(ViewCommand.MESSAGE_SUCCESS,
                "statistics"),
                false, false,
                false, true, false, false, CommandResult.EMPTY_BYTE_ARRAY_DATA);
        assertCommandSuccess(new ViewCommand(View.STATISTICS), model, expectedCommandResult, expectedModel);
    }
}
