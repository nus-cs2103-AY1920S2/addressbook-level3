package seedu.zerotoone.logic.commands;

import static seedu.zerotoone.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
import static seedu.zerotoone.testutil.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
