package seedu.expensela.model;

import javafx.collections.ObservableList;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;

/**
 * Unmodifiable view of an expensela
 */
public interface ReadOnlyExpenseLa {

    /**
     * Returns an unmodifiable view of the transactions list.
     * This list will not contain any duplicate transactions.
     */
    ObservableList<Transaction> getTransactionList();

    MonthlyData getMonthlyData();

    ToggleView getToggleView();

    Filter getFilter();
}
