package seedu.expensela.model.monthlydata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MonthlyDataTest {

    private final MonthlyData monthlyData = new MonthlyData("8", new Budget("500.00"),
            new Expense("100.00"), new Income("1000.00"));

    @Test
    public void setExpense_setsCorrect() {
        Expense newExpense = new Expense("200.00");
        monthlyData.setExpense(newExpense);
        assertEquals(monthlyData.getExpense(), new Expense("200.00"));
    }

    @Test
    public void setBudget_setsCorrect() {
        Budget newBudget = new Budget("800.00");
        monthlyData.setBudget(newBudget);
        assertEquals(monthlyData.getBudget(), new Budget("800.00"));
    }

    @Test
    public void setIncome_setsCorrect() {
        Income newIncome = new Income("1500.00");
        monthlyData.setIncome(newIncome);
        assertEquals(monthlyData.getIncome(), new Income("1500.00"));
    }
}
