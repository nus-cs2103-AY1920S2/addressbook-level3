package seedu.expensela.logic.commands;

import static seedu.expensela.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import org.junit.jupiter.api.Test;

import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.UserPrefs;

public class ToggleCommandTest {
    private Model model;
    private Model expectedModel;

    @Test
    public void execute_toggleSwitch() {
        model = new ModelManager(getTypicalExpenseLa(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalExpenseLa(), new UserPrefs());
        expectedModel.switchToggleView();

        assertCommandSuccess(new ToggleCommand(), model, ToggleCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
