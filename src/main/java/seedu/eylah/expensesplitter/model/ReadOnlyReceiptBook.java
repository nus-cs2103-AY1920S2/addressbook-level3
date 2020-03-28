package seedu.eylah.expensesplitter.model;

import javafx.collections.ObservableList;
import seedu.eylah.expensesplitter.model.receipt.Receipt;

/**
 * Unmodifiable view of an ReceiptBook book.
 */
public interface ReadOnlyReceiptBook {

    /**
     * Returns an unmodifiable view of the Receipt list.
     * This list will not contain any duplicate Receipt.
     */
    ObservableList<Receipt> getReceiptList();
}
