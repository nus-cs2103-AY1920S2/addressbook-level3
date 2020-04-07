package seedu.expensela.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.UserPrefs;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.monthlydata.Expense;
import seedu.expensela.model.monthlydata.Income;
import seedu.expensela.model.monthlydata.MonthlyData;

public class BudgetCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpenseLa(), new UserPrefs(), new GlobalData());
        expectedModel = new ModelManager(model.getExpenseLa(), new UserPrefs(), new GlobalData());
        expectedModel.setMonthlyData(new MonthlyData("1", new Budget("1500"),
                new Expense("500"), new Income("2000")));
    }

    @Test
    public void execute_budgetChanged_nonRecurring() throws Exception {
        CommandResult result = new BudgetCommand(1500.0, false).execute(model);

        assertEquals(String.format(BudgetCommand.MESSAGE_SUCCESS, 1500.00), result.getFeedbackToUser());
        assertEquals(model, expectedModel);
    }
}
