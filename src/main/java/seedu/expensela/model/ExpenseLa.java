package seedu.expensela.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.monthlydata.Expense;
import seedu.expensela.model.monthlydata.Income;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.DateEqualsKeywordPredicate;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.model.transaction.TransactionList;

/**
 * Wraps all data at the expensela level
 * Duplicates are not allowed (by .isSameTransaction comparison)
 */
public class ExpenseLa implements ReadOnlyExpenseLa {
    private final Filter filter;
    private final MonthlyData monthlyData;
    private final TransactionList transactions;
    private final ToggleView toggleView;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        String date = LocalDate.now().toString().substring(0, 7);
        filter = new Filter(null, new DateEqualsKeywordPredicate(Arrays.asList(date)));
        monthlyData = new MonthlyData("1",
                new Budget("0.00"), new Expense("0.00"), new Income("0.00"));
        transactions = new TransactionList();
        toggleView = new ToggleView();
    }

    public ExpenseLa() {}

    /**
     * Creates an ExpenseLa using the Transactions in the {@code toBeCopied}
     */
    public ExpenseLa(ReadOnlyExpenseLa toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the transaction list with {@code transactions}.
     * {@code transactions} must not contain duplicate transactions.
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setTransaction(transactions);
    }

    public void setMonthlyData(MonthlyData monthlyData) {
        this.monthlyData.setBudget(monthlyData.getBudget());
        this.monthlyData.setExpense(monthlyData.getExpense());
        this.monthlyData.setIncome(monthlyData.getIncome());
    }

    public void setFilter(Filter filter) {
        this.filter.setFilterCategoryName(filter.getCategoryNamePredicate());
        this.filter.setDateMonth(filter.getDateMonthPredicate());
    }

    public boolean getIsFilterMonth() {
        return this.filter.isFilterMonth();
    }

    /**
     * Resets the existing data of this {@code ExpenseLa} with {@code newData}.
     */
    public void resetData(ReadOnlyExpenseLa newData) {
        requireNonNull(newData);

        setTransactions(newData.getTransactionList());
        setMonthlyData(newData.getMonthlyData());
        setFilter(newData.getFilter());
    }

    //// transaction-level operations

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in the expensela.
     */
    public boolean hasTransaction(Transaction transaction) {
        requireNonNull(transaction);
        return transactions.contains(transaction);
    }

    /**
     * Adds a transaction to the expensela.
     * The transaction must not already exist in the expensela.
     */
    public void addTransaction(Transaction p) {
        transactions.add(p);
    }

    /**
     * Replaces the given transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the expensela.
     * The transaction identity of {@code editedTransaction} must not be the same as another
     * existing transaction in the expensela.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireNonNull(editedTransaction);

        transactions.setTransaction(target, editedTransaction);
    }

    /**
     * Removes {@code key} from this {@code ExpenseLa}.
     * {@code key} must exist in the address book.
     */
    public void removeTransaction(Transaction key) {
        transactions.remove(key);
    }

    public void switchToggleView() {
        toggleView.switchIsViewList();
    }

    //// util methods

    @Override
    public String toString() {
        return transactions.asUnmodifiableObservableList().size() + " transactions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions.asUnmodifiableObservableList();
    }

    @Override
    public MonthlyData getMonthlyData() {
        return this.monthlyData;
    }

    @Override
    public ToggleView getToggleView() {
        return this.toggleView;
    }

    public Filter getFilter() {
        return this.filter;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseLa // instanceof handles nulls
                && transactions.equals(((ExpenseLa) other).transactions));
    }

    @Override
    public int hashCode() {
        return transactions.hashCode();
    }
}
