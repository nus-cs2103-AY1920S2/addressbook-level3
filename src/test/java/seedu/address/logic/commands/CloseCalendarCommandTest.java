package seedu.address.logic.commands;

import static seedu.address.logic.commands.CloseCalendarCommand.CLOSE_CALENDAR_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CloseCalendarCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_calendar_success() {
        CommandResult expectedCommandResult = new CommandResult(CLOSE_CALENDAR_MESSAGE, true);
        assertCommandSuccess(new CloseCalendarCommand(), model, expectedCommandResult, expectedModel);
    }
}
