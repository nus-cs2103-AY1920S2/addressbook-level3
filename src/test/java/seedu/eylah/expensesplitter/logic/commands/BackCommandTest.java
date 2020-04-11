package seedu.eylah.expensesplitter.logic.commands;

import static seedu.eylah.expensesplitter.logic.commands.BackCommand.MESSAGE_SUCCESS;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.SplitterModelManager;

public class BackCommandTest {

    private SplitterModel splitterModel = new SplitterModelManager();
    private SplitterModel expectedSplitterModel = new SplitterModelManager();

    @Test
    public void execute_back_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, false, true);
        assertCommandSuccess(new BackCommand(), splitterModel, expectedCommandResult, expectedSplitterModel);
    }

}
