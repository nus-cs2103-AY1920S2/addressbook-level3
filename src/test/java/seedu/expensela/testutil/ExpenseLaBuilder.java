package seedu.expensela.testutil;

import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class to help with building ExpenseLa objects.
 * Example usage: <br>
<<<<<<< HEAD
 *     {@code ExpenseLa ab = new ExpenseLaBuilder().withTransaction("John", "Doe").build();}
=======
 *     {@code ExpenseLa el = new ExpenseLaBuilder().withTransaction(transaction_1).build();}
>>>>>>> 30892643437f34849fd9499b977cc4141118a5e7
 */
public class ExpenseLaBuilder {

    private ExpenseLa expenseLa;

    public ExpenseLaBuilder() {
        expenseLa = new ExpenseLa();
    }

    public ExpenseLaBuilder(ExpenseLa expenseLa) {
        this.expenseLa = expenseLa;
    }

    /**
     * Adds a new {@code Transaction} to the {@code ExpenseLa} that we are building.
     */
    public ExpenseLaBuilder withTransaction(Transaction transaction) {
        expenseLa.addTransaction(transaction);
        return this;
    }

    /**
     * Sets the {@code MonthlyData} of the {@code ExpenseLa} that we are building.
     */
    public ExpenseLaBuilder withMonthlyData(MonthlyData monthlyData) {
        expenseLa.setMonthlyData(monthlyData);
        return this;
    }

    public ExpenseLa build() {
        return expenseLa;
    }
}
