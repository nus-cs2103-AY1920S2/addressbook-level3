package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Month;
import java.time.Year;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.transaction.exceptions.TransactionNotFoundException;

/**
 * A list of transactions that does not allow nulls.
 * Generics are used to allow creating list of expenses and list of incomes.
 *
 * Supports a minimal set of list operations.
 */
public class TransactionList<T extends Transaction> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    // @@author cheyannesim
    /**
     * Returns the total amount in a the transaction list.
     */
    public Amount getTotal() {
        return internalList
            .stream()
            .map(Transaction::getAmount)
            .reduce(Amount.zero(), Amount::add);
    }
    // @@author

    /**
     * Returns true if the list contains an equivalent transaction as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a Transaction to the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
        sort();
    }

    /**
     * Replaces the transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the list.
     */
    public void setTransaction(T target, T editedTransaction) {
        requireAllNonNull(target, editedTransaction);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TransactionNotFoundException();
        }

        internalList.set(index, editedTransaction);
        sort();
    }

    /**
     * Removes the equivalent transaction from the list.
     * The transaction must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TransactionNotFoundException();
        }
        sort();
    }


    public void setTransactions(TransactionList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        sort();
    }

    /**
     * Replaces the contents of this list with {@code transactions}.
     */
    public void setTransactions(List<T> transactions) {
        requireAllNonNull(transactions);
        internalList.setAll(transactions);
        sort();
    }

    /**
     * Retrieves a list of expenses filtered by the month they were added, using {@code date}.
     */
    public TransactionList<T> getTransactionsInMonth(Month month, Year year) {
        TransactionList<T> filteredTransactions = new TransactionList<T>();

        for (T transaction : this) {
            if (transaction.getDate().getMonth().equals(month) && transaction.getDate().getYear().equals(year)) {
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }

    /**
     * Sorts the {@code internalList} by date.
     */
    public void sort() {
        FXCollections.sort(internalList, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return -o1.getDate().compareTo(o2.getDate());
            }
        });
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionList<?> // instanceof handles nulls
                        && internalList.equals(((TransactionList<?>) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
