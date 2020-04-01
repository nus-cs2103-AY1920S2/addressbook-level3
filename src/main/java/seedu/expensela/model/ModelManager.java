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
import seedu.expensela.model.monthlydata.Expense;
import seedu.expensela.model.monthlydata.Income;
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
    private final Filter filter;

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
        filter = this.expenseLa.getFilter();
        updateFilteredTransactionList(filter.getCategoryNamePredicate(), filter.getDateMonthPredicate());
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
        boolean positive = target.getAmount().positive;
        double amount = target.getAmount().transactionAmount;
        updateMonthlyData(positive, amount * (-1));
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS, PREDICATE_SHOW_ALL_TRANSACTIONS);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        expenseLa.addTransaction(transaction);
        boolean positive = transaction.getAmount().positive;
        double amount = transaction.getAmount().transactionAmount;
        updateMonthlyData(positive, amount);
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS, PREDICATE_SHOW_ALL_TRANSACTIONS);
    }

    /**
     * Update monthly data depending whether it is a positive transaction and the amount
     * @param positive whether transaction is income or expense
     * @param amount positive for transaction insertion, negative for deletion
     */
    public void updateMonthlyData(boolean positive, double amount) {
        if (this.expenseLa.getMonthlyData() == null) {
            return;
        }
        if (positive) {
            this.expenseLa.getMonthlyData()
                    .setIncome(new Income(Double.toString(
                            this.expenseLa.getMonthlyData().getIncome().incomeAmount + amount)));
            userPrefs.setTotalBalance(userPrefs.getTotalBalance() + amount);
        } else {
            this.expenseLa.getMonthlyData()
                    .setExpense(new Expense(Double.toString(
                            this.expenseLa.getMonthlyData().getExpense().expenseAmount + amount)));
            userPrefs.setTotalBalance(userPrefs.getTotalBalance() - amount);
        }
    }

    @Override
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);
        boolean positiveBefore = target.getAmount().positive;
        double amountBefore = target.getAmount().transactionAmount * -1;
        boolean positive = editedTransaction.getAmount().positive;
        double amount = editedTransaction.getAmount().transactionAmount;
        updateMonthlyData(positiveBefore, amountBefore);
        updateMonthlyData(positive, amount);
        expenseLa.setTransaction(target, editedTransaction);
    }

    //=========== Filtered Transaction List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Transaction} backed by the internal list of
     * {@code versionedExpenseLa}
     */

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return unfilteredTransactions;
    }

    /**
    * Applies setPredicate(predicate) to 2 different filteredLists and then making unfilteredTransactions
     * a filteredList containing the elements list 1 and 2 have in common.
     */
    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate1, Predicate<Transaction> predicate2) {
        if (predicate1 != null && predicate2 != null) {
            Predicate<Transaction> predicate = predicate1.and(predicate2);
            unfilteredTransactions.setPredicate(predicate);
        } else if (predicate1 != null && predicate2 == null) {
            unfilteredTransactions.setPredicate(predicate1);
        } else if (predicate1 == null && predicate2 != null) {
            unfilteredTransactions.setPredicate(predicate2);
        } else {
            throw new NullPointerException();
        }

    }



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
        return this.expenseLa.getMonthlyData();
    }

    /**
     * Update monthly data object
     */
    @Override
    public void setMonthlyData(MonthlyData monthlyData) {
        expenseLa.setMonthlyData(monthlyData);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    /**
     * Update filter object
     */
    @Override
    public void setFilter(Filter filter) {
        expenseLa.setFilter(filter);
        updateFilteredTransactionList(filter.getCategoryNamePredicate(), filter.getDateMonthPredicate());
    }

    @Override
    public Double getTotalBalance() {
        return userPrefs.getTotalBalance();
    }

    @Override
    public void updateTotalBalance(Double balance) {
        userPrefs.setTotalBalance(balance);
        System.out.println(userPrefs.getTotalBalance());
    }

    //=========== Monthly Data Accessors =============================================================
    /**
     * Returns toggle list or chart object
     */
    @Override
    public ToggleView getToggleView() {
        return this.expenseLa.getToggleView();
    }

    @Override
    public void switchToggleView() {
        expenseLa.switchToggleView();
    }
}
