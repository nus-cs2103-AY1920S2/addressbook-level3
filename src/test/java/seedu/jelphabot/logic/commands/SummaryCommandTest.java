//@@author eedenong
package seedu.jelphabot.logic.commands;

import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;

public class SummaryCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_summary_success() {
        CommandResult expectedCommandResult =
            new CommandResult(SummaryCommand.MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT).isShowSummary();
        assertCommandSuccess(new SummaryCommand(), model, expectedCommandResult, expectedModel);
    }
}
