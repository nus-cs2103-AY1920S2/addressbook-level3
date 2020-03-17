package seedu.expensela.model;

import javafx.collections.ObservableList;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyExpenseLa {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Transaction> getTransactionList();

    MonthlyData getMonthlyData();
}
