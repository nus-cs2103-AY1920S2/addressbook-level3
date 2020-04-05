package seedu.expensela.model;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.transaction.Transaction;

/**
 *
 */
public interface ReadOnlyGlobalData {

    Balance getTotalBalance();

    Budget getRecurringBudget();

    ObservableList<Transaction> getRecurringTransactionList();

    LocalDate getLastUpdatedDate();
}
