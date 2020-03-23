package seedu.address.model.order.exceptions;

/**
 * Signals that the operation will result in duplicate Return Orders (Orders are considered duplicates if they have the
 * same identity).
 */
public class DuplicateReturnOrderException extends RuntimeException {
    public DuplicateReturnOrderException() {
        super("Operation would result in duplicate return orders");
    }
}
