package seedu.expensela.model;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.expensela.commons.core.GuiSettings;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;

/**
 * Represents the in-memory model of the expensela data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ExpenseLa expenseLa;
    private final UserPrefs userPrefs;
    private final FilteredList<Transaction> unfilteredTransactions;
    private final FilteredList<Transaction> filteredTransactions;
    private final MonthlyData monthlyData;

    /**
     * Initializes a ModelManager with the given expenseLa and userPrefs.
     */
    public ModelManager(ReadOnlyExpenseLa expenseLa, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(expenseLa, userPrefs);

        logger.fine("Initializing with expenseLa: " + expenseLa + " and user prefs " + userPrefs);

        this.expenseLa = new ExpenseLa(expenseLa);
        this.userPrefs = new UserPrefs(userPrefs);
        unfilteredTransactions = new FilteredList<>(this.expenseLa.getTransactionList());
        filteredTransactions = new FilteredList<>(this.expenseLa.getTransactionList());
        monthlyData = this.expenseLa.getMonthlyData();
    }

    public ModelManager() {
        this(new ExpenseLa(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getExpenseLaFilePath() {
        return userPrefs.getExpenseLaFilePath();
    }

    @Override
    public void setExpenseLaFilePath(Path expenseLaFilePath) {
        requireNonNull(expenseLaFilePath);
        userPrefs.setExpenseLaFilePath(expenseLaFilePath);
    }

    //=========== ExpenseLa ================================================================================

    @Override
    public void setExpenseLa(ReadOnlyExpenseLa expenseLa) {
        this.expenseLa.resetData(expenseLa);
    }

    @Override
    public ReadOnlyExpenseLa getExpenseLa() {
        return expenseLa;
    }

    @Override
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return expenseLa.hasTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Transaction target) {
        expenseLa.removeTransaction(target);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        expenseLa.addTransaction(transaction);
        updateUnfilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);

        expenseLa.setTransaction(target, editedTransaction);
    }

    //=========== Unfiltered Transaction List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Transaction} backed by the internal list of
     * {@code versionedExpenseLa}
     */

    @Override
    public ObservableList<Transaction> getUnfilteredTransactionList() {
        return unfilteredTransactions;
    }

    @Override
    public void updateUnfilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        unfilteredTransactions.setPredicate(predicate);
    }

    //=========== Filtered Transaction List Accessors =============================================================

//    /**
//     * Returns an unmodifiable view of the list of {@code Transaction} backed by the internal list of
//     * {@code versionedExpenseLa} with the filters applied
//     */
//
//    @Override
//    public ObservableList<Transaction> getListForFilter() {
//        return filteredTransactions;
//    }
//
//    @Override
//    public void updateListForFilter(Predicate<Transaction> predicate) {
//        requireNonNull(predicate);
//
//        // modifies the filteredTransactions list to only contain the transactions with the category name or date range
//        // matching the predicate
//        filteredTransactions.setPredicate(predicate);
//    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return expenseLa.equals(other.expenseLa)
                && userPrefs.equals(other.userPrefs)
                && unfilteredTransactions.equals(other.unfilteredTransactions);
    }

    //=========== Monthly Data Accessors =============================================================
    /**
     * Returns monthly data object
     */
    @Override
    public MonthlyData getMonthlyData() {
        return monthlyData;
    }

    /**
     * Update monthly data object
     */
    @Override
    public void updateMonthlyData(MonthlyData monthlyData) {
        expenseLa.setMonthlyData(monthlyData);
    }
}
