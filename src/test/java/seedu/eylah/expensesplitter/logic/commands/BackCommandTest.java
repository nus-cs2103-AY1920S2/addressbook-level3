package seedu.eylah.expensesplitter.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, false, true);
        assertCommandSuccess(new BackCommand(), splitterModel, expectedCommandResult, expectedSplitterModel);
    }

    @Test
    public void equals() {
        BackCommand backCommand = new BackCommand();

        // same object -> returns true
        assertTrue(backCommand.equals(backCommand));

        // different types -> returns false
        assertFalse(backCommand.equals(1));

        //null -> returns false
        assertFalse(backCommand.equals(null));
    }

}
