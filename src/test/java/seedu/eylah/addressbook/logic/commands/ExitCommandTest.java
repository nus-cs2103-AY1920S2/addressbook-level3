package seedu.eylah.addressbook.logic.commands;

import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.addressbook.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.eylah.addressbook.model.Model;
import seedu.eylah.addressbook.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
