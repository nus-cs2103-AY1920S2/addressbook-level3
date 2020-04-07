package seedu.expensela.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.expensela.model.transaction.exceptions.DuplicateTransactionException;
import seedu.expensela.model.transaction.exceptions.TransactionNotFoundException;

/**
 * A list of transactions that enforces uniqueness between its elements and does not allow nulls.
 * A transaction is considered unique by comparing using {@code Transaction#isSameTransactionTransaction)}.
 * As such, adding and updating of transaction uses Transaction#isSameTransaction(Transaction) for equality so as to
 * ensure that the transaction being added or updated is unique in terms of identity in the UniqueTransactionList.
 * However, the removal of a transaction uses Transaction#equals(Object) so
 * as to ensure that the transaction with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Transaction#isSameTransaction(Transaction)
 */
public class TransactionList implements Iterable<Transaction> {

    private final ObservableList<Transaction> internalList = FXCollections.observableArrayList();
    private final ObservableList<Transaction> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent transaction as the given argument.
     */
    public boolean contains(Transaction toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTransaction);
    }

    /**
     * Adds a transaction to the list.
     * The transaction must not already exist in the list.
     */
    public void add(Transaction toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTransactionException();
        }
        internalList.add(toAdd);
        FXCollections.sort(internalList);
    }

    /**
     * Replaces the transaction {@code target} in the list with {@code editedTransaction}.
     * {@code target} must exist in the list.
     * The transaction identity of {@code editedTransaction} must not be the same as another
     * existing transaction in the list.
     */
    public void setTransaction(Transaction target, Transaction editedTransaction) {
        requireAllNonNull(target, editedTransaction);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TransactionNotFoundException();
        }

        if (!target.isSameTransaction(editedTransaction) && contains(editedTransaction)) {
            throw new DuplicateTransactionException();
        }

        internalList.set(index, editedTransaction);
        FXCollections.sort(internalList);
    }

    public void setTransaction(TransactionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        FXCollections.sort(internalList);
    }

    /**
     * Replaces the contents of this list with {@code transaction}.
     * {@code transaction} must not contain duplicate transaction.
     */
    public void setTransaction(List<Transaction> transactions) {
        requireAllNonNull(transactions);
        internalList.setAll(transactions);
        FXCollections.sort(internalList);
    }

    /**
     * Removes the equivalent transaction from the list.
     * The transaction must exist in the list.
     */
    public void remove(Transaction toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TransactionNotFoundException();
        }
        FXCollections.sort(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Transaction> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Transaction> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransactionList // instanceof handles nulls
                        && internalList.size() == ((TransactionList) other).internalList.size());
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    public void clear() {
        internalList.clear();
    }
}
