package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.transaction.exceptions.DuplicateTransactionException;


/**
 * Implements a list of non-duplicate transactions
 */
public class UniqueTransactionList implements Iterable<Transaction> {
    private final ObservableList<Transaction> internalList = FXCollections.observableArrayList();
    private final ObservableList<Transaction> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent transaction as the given argument.
     * @param toCheck the transaction to be checked
     * @return true if there is an equivalent transaction in the list.
     */
    public boolean contains(Transaction toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTransaction);
    }

    /**
     * Adds a transaction to the unique list.
     * @param toAdd transaction to be added.
     */
    public void add(Transaction toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTransactionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Transaction> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Transaction> iterator() {
        return null;
    }
}
