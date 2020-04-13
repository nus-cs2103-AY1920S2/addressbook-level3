package seedu.expensela.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.model.transaction.TransactionList;

/**
 * Global User data with recurring budget, transactions and balance information
 */
public class GlobalData implements ReadOnlyGlobalData {

    private Budget recurringBudget = new Budget("0.0");
    private TransactionList recurringTransactionsList = new TransactionList();
    private Balance totalBalance = new Balance("0.0");
    private LocalDate lastUpdatedDate = LocalDate.now();

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public GlobalData() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public GlobalData(ReadOnlyGlobalData globalData) {
        this();
        resetData(globalData);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyGlobalData globalData) {
        requireNonNull(globalData);
        setLastUpdatedDate(globalData.getLastUpdatedDate());
        setRecurringBudget(globalData.getRecurringBudget());
        setRecurringTransactionsList(globalData.getRecurringTransactionList());
        setTotalBalance(globalData.getTotalBalance());
    }

    @Override
    public Balance getTotalBalance() {
        return totalBalance;
    }

    @Override
    public Budget getRecurringBudget() {
        return recurringBudget;
    }

    @Override
    public ObservableList<Transaction> getRecurringTransactionList() {
        return recurringTransactionsList.asUnmodifiableObservableList();
    }

    @Override
    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setTotalBalance(Balance balance) {
        this.totalBalance = balance;
    }

    public void setRecurringBudget(Budget budget) {
        this.recurringBudget = budget;
    }

    public void setRecurringTransactionsList(ObservableList<Transaction> recurringTransactionsList) {
        this.recurringTransactionsList.setTransaction(recurringTransactionsList);
    }

    public void clearRecurringTransactionsList() {
        this.recurringTransactionsList.clear();
    }

    public void setLastUpdatedDate(LocalDate date) {
        this.lastUpdatedDate = date;
    }

    public void addTransaction(Transaction toAdd) {
        recurringTransactionsList.add(toAdd);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GlobalData)) { //this handles null as well.
            return false;
        }

        GlobalData o = (GlobalData) other;

        return (recurringBudget.equals(o.recurringBudget))
                && recurringTransactionsList.equals(o.recurringTransactionsList)
                && totalBalance.equals(o.totalBalance)
                && lastUpdatedDate.equals(o.lastUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalBalance, recurringBudget, recurringTransactionsList, lastUpdatedDate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Balance : " + totalBalance);
        sb.append("\nRecurring Budget : " + recurringBudget);
        sb.append("\nRecurring Transactions : " + recurringTransactionsList);
        sb.append("\nLast Updated Date : " + lastUpdatedDate);
        return sb.toString();
    }

}
