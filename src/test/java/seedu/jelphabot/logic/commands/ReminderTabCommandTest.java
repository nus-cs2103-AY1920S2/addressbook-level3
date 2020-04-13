//@@author yapdianhao
package seedu.jelphabot.logic.commands;

import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jelphabot.logic.commands.ReminderTabCommand.MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;

public class ReminderTabCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_productivity_success() {
        CommandResult expectedCommandResult =
            new CommandResult(MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT).isShowReminder();
        assertCommandSuccess(new ReminderTabCommand(), model, expectedCommandResult, expectedModel);
    }
}
