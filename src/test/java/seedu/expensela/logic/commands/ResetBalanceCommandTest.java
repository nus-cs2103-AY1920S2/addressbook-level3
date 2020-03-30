package seedu.expensela.logic.commands;

import static seedu.expensela.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.UserPrefs;

public class ResetBalanceCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpenseLa(), new UserPrefs());
        UserPrefs up = new UserPrefs();
        up.setTotalBalance(0.0);
        expectedModel = new ModelManager(model.getExpenseLa(), up);
    }

    @Test
    public void execute_resetBalance() {
        assertCommandSuccess(new ResetBalanceCommand(), model, ResetBalanceCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
