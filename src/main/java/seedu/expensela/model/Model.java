package seedu.expensela.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.expensela.commons.core.GuiSettings;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;

/**
 * The API of the Model component.
 */
public interface Model {

    /** {@code Predicate} that always evaluate to true */
    Predicate<Transaction> PREDICATE_SHOW_ALL_TRANSACTIONS = unused -> true;

    /**
     * Get Command from command history
     * @param offset
     * @return Command String
     */
    String getCommandFromHistory(int offset);

    /**
     * Add Command to history
     * @param command
     */
    void addToCommandHistory(String command);

    /**
     * Delete command from history
     * @param command
     */
    void deleteFromCommandHistory(String command);

    int getCommandHistorySize();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' transaction list file path.
     */
    Path getExpenseLaFilePath();

    /**
     * Sets the user prefs' transaction list file path.
     */
    void setExpenseLaFilePath(Path expenseLaFilePath);

    /**
     * Returns the user global data file path.
     */
    Path getGlobalDataFilePath();

    /**
     * Sets the user global data file path.
     */
    void setGlobalDataFilePath(Path expenseLaFilePath);

    /**
     * Replaces transaction list data with the data in {@code expenseLa}.
     */
    void setExpenseLa(ReadOnlyExpenseLa expenseLa);

    /** Returns the ExpenseLa */
    ReadOnlyExpenseLa getExpenseLa();

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in the transaction list.
     */
    boolean hasTransaction(Transaction transaction);

    /**
     * Deletes the given transaction.
     * The transaction must exist in the transaction list.
     */
    void deleteTransaction(Transaction target);

    /**
     * Adds the given transaction.
     * {@code transaction} must not already exist in the transaction list.
     */
    void addTransaction(Transaction transaction);

    /**
     * Replaces the given transaction {@code target} with {@code editedTransaction}.
     * {@code target} must exist in the transaction list.
     * The transaction identity of {@code editedTransaction} must not be the same as another
     * existing transaction in the transaction list.
     */
    void setTransaction(Transaction target, Transaction editedTransaction);

    /** Returns an unmodifiable view of the unfiltered transaction list */
    ObservableList<Transaction> getFilteredTransactionList();

    /**
     * Updates the filter of the unfiltered transaction list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTransactionList(Predicate<Transaction> predicate1, Predicate<Transaction> predicate2);

    /** Returns a view of monthly data object */
    MonthlyData getMonthlyData();

    /** Updates monthly data by the given monthly data */
    void setMonthlyData(MonthlyData monthlyData);

    Filter getFilter();

    void setFilter(Filter filter);

    /** Returns a boolean value of whether filter is filtered my month */
    boolean getIsFilterMonth();

    /** Returns a view of toggleView object */
    ToggleView getToggleView();

    /** Switches the toggle in toggleView object */
    void switchToggleView();

    /**
     * Get Global data of the user.
     * @return global data object
     */
    GlobalData getGlobalData();

    /**
     * Set Global data of the user.
     */
    void setGlobalData(GlobalData globalData);

    /**
     * Get Total balance of the user.
     * @return balance object
     */
    Balance getTotalBalance();

    /**
     * Updates total balance of the user.
     */
    void updateTotalBalance(Balance balance);

    /**
     * Adds a recurring transaction to the Global Data
     */
    void addTransactionToGlobalData(Transaction transaction);

    /**
     * Resets the Global Data and clears it of all recurring transactions
     */
    void clearRecurringTransactions();

}
