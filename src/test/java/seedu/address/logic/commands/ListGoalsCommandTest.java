package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ListGoalsCommand.LIST;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ListGoalsCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_listGoals_success() {
        CommandResult expectedCommandResult = new CommandResult(LIST, false, null, false);
        assertCommandSuccess(new ListGoalsCommand(), model, expectedCommandResult, expectedModel);
    }
}
