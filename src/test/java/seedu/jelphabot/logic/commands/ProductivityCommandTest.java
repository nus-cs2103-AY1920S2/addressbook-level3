// @@author Clouddoggo

package seedu.jelphabot.logic.commands;

import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jelphabot.logic.commands.ProductivityCommand.MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;

public class ProductivityCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_productivity_success() {
        CommandResult expectedCommandResult =
            new CommandResult(MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT).isShowProductivity();
        assertCommandSuccess(new ProductivityCommand(), model, expectedCommandResult, expectedModel);
    }
}
