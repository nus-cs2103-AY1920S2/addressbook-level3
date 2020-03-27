package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ShowCommand.SHOW_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ShowCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_show_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOW_MESSAGE, false, false, false, true);
        assertCommandSuccess(new ShowCommand(), model, expectedCommandResult, expectedModel);
    }
}
