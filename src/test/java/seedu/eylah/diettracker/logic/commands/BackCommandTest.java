package seedu.eylah.diettracker.logic.commands;

import static seedu.eylah.diettracker.logic.commands.BackCommand.MESSAGE_SUCCESS;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.DietModelManager;

public class BackCommandTest {

    private DietModel splitterModel = new DietModelManager();
    private DietModel expectedSplitterModel = new DietModelManager();

    @Test
    public void execute_back_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, true);
        assertCommandSuccess(new BackCommand(), splitterModel, expectedCommandResult, expectedSplitterModel);
    }

}

