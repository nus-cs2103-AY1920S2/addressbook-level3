package seedu.eylah.expensesplitter.model.receipt.exceptions;

/**
 * Signals that the operation will result in duplicate Receipt (Receipt are considered duplicates if they have the same
 * identity).
 */
public class DuplicateReceiptException extends RuntimeException {

    public DuplicateReceiptException() {
        super("Operation would result in duplicate receipts");
    }
}
