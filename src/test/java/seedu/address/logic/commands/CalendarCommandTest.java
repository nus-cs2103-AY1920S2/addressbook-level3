package seedu.address.logic.commands;

import static seedu.address.logic.commands.CalenderCommand.SWITCHED_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CalendarCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_switch_success() {
        CommandResult expectedCommandResult = new CommandResult(SWITCHED_MESSAGE, false, false, true);
        assertCommandSuccess(new CalenderCommand(), model, expectedCommandResult, expectedModel);
    }
}
