package seedu.expensela.logic.commands;

import static seedu.expensela.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import org.junit.jupiter.api.Test;

import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyExpenseLa_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyExpenseLa_success() {
        Model model = new ModelManager(getTypicalExpenseLa(), new UserPrefs(), new GlobalData());
        Model expectedModel = new ModelManager(getTypicalExpenseLa(), new UserPrefs(), new GlobalData());
        expectedModel.setExpenseLa(new ExpenseLa());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
