package seedu.address.model;

import java.time.Month;
import java.time.Year;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.Expense;
import seedu.address.model.transaction.Income;
import seedu.address.model.transaction.Transaction;

/**
 * Unmodifiable view of a Wallet
 */
public interface ReadOnlyWallet {

    /**
     * Returns an unmodifiable view of the list of Transactions.
     */
    ObservableList<Transaction> getTransactionList();

    /**
     * Returns an unmodifiable view of the list of Incomes.
     */
    ObservableList<Income> getIncomeList();

    /**
     * Returns an unmodifiable view of the list of Expenses.
     */
    ObservableList<Expense> getExpenseList();

    /**
     * Returns an unmodifiable view of the list of Budgets.
     */
    ObservableList<Budget> getBudgetList();

    /**
     * Returns the unmodifiable budget specified for the month and year, if any.
     */
    Budget getBudget(Month month, Year year);

    /**
     * Returns the default budget set by the user.
     */
    Budget getDefaultBudget();
}
