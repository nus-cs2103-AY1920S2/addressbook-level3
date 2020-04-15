package seedu.address.model.transaction.exceptions;

/**
 * Signals of duplicate transactions
 */
public class DuplicateTransactionException extends RuntimeException {
    public DuplicateTransactionException() {
        super("Operation would result in duplicate transactions");
    }
}
