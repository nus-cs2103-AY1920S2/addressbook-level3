package seedu.eylah.expensesplitter.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.expensesplitter.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.expensesplitter.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.SplitterModelManager;

public class ExitCommandTest {

    private SplitterModel splitterModel = new SplitterModelManager();
    private SplitterModel expectedSplitterModel = new SplitterModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, true, false);
        assertCommandSuccess(new ExitCommand(), splitterModel, expectedCommandResult, expectedSplitterModel);
    }


    @Test
    public void equals() {
        ExitCommand exitCommand = new ExitCommand();

        // same object -> returns true
        assertTrue(exitCommand.equals(exitCommand));

        // different types -> returns false
        assertFalse(exitCommand.equals(1));

        //null -> returns false
        assertFalse(exitCommand.equals(null));

    }

}
