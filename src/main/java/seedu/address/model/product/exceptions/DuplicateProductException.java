package seedu.address.model.product.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException() {
        super("Operation would result in duplicate persons");
    }
}
