package seedu.expensela.testutil;

import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class to help with building ExpenseLa objects.
 * Example usage: <br>
 *     {@code ExpenseLa ab = new ExpenseLaBuilder().withTransaction("John", "Doe").build();}
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

    public ExpenseLa build() {
        return expenseLa;
    }
}
