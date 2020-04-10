package seedu.delino.logic.commands;

import static seedu.delino.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.delino.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.delino.model.Model;
import seedu.delino.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult =
                new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, false, false);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
