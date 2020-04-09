package seedu.expensela.logic.commands;

import static seedu.expensela.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import org.junit.jupiter.api.Test;

import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.UserPrefs;

public class ToggleViewCommandTest {
    private Model model;
    private Model expectedModel;

    @Test
    public void execute_toggleViewSwitch() {
        model = new ModelManager(getTypicalExpenseLa(), new UserPrefs(), new GlobalData());
        expectedModel = new ModelManager(getTypicalExpenseLa(), new UserPrefs(), new GlobalData());
        expectedModel.switchToggleView();

        assertCommandSuccess(new ToggleViewCommand(), model, ToggleViewCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
