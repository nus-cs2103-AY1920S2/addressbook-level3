package seedu.eylah.diettracker.logic.commands;

import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.diettracker.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.DietModelManager;

public class ExitCommandTest {

    private DietModel splitterModel = new DietModelManager();
    private DietModel expectedSplitterModel = new DietModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, true, false);
        assertCommandSuccess(new ExitCommand(), splitterModel, expectedCommandResult, expectedSplitterModel);
    }

}
