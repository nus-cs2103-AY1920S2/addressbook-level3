package seedu.address.model;

import seedu.address.model.transaction.Transaction;

/**
 * A {@code TransactionHistory} that keeps track of its history. Snapshots of its state are done based on external
 * commands.
 */
public class VersionedTransactionHistory extends TransactionHistory implements Version<TransactionHistory> {
    private LinearHistory<TransactionHistory> history;

    public VersionedTransactionHistory() {
        super();
        history = new LinearHistory<>(super.copy());
    }

    /**
     * Creates a VersionedTransactionHistory using the {@code Transaction}s in the {@code toBeCopied}.
     */
    public VersionedTransactionHistory(ReadOnlyList<Transaction> toBeCopied) {
        history = new LinearHistory<>(new TransactionHistory(toBeCopied));
        updateDisplayedTransactions();
    }

    //=========== List Overwrite Operations =========================================================================

    /**
     * Resets the existing data of this {@code VersionedTransactionHistory} with {@code newData}.
     * Resets the history to an empty state as well.
     */
    public void resetData(ReadOnlyList<Transaction> newData) {
        getCurrentState().setTransactions(newData.getReadOnlyList());
        updateDisplayedTransactions();
    }

    //=========== Transaction-Level Operations =========================================================================

    /**
     * Returns true if a transaction with the same identity as {@code transaction} exists in the current state.
     */
    public boolean hasTransaction(Transaction transaction) {
        return getCurrentState().hasTransaction(transaction);
    }

    /**
     * Adds a transaction to the current state.
     * The transaction must not already exist in the current state.
     */
    public void addTransaction(Transaction p) {
        getCurrentState().addTransaction(p);
        updateDisplayedTransactions();
    }

    /**
     * Removes {@code key} from the current state.
     * {@code key} must exist in the current state.
     */
    public void removeTransaction(Transaction key) {
        getCurrentState().removeTransaction(key);
        updateDisplayedTransactions();
    }

    //=========== Versioning Methods =========================================================================

    @Override
    public void commit() {
        history.commit();
    }

    @Override
    public void undo() throws StateNotFoundException {
        history.undo();
        updateDisplayedTransactions();
    }

    @Override
    public void redo() throws StateNotFoundException {
        history.redo();
        updateDisplayedTransactions();
    }

    @Override
    public TransactionHistory getCurrentState() {
        return history.getCurrentState();
    }

    //=========== Util Methods =========================================================================

    /**
     * Updates the list of suppliers to be shown in the UI.
     */
    private void updateDisplayedTransactions() {
        super.resetData(getCurrentState());
    }
}
