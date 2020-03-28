package seedu.eylah.expensesplitter.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.eylah.expensesplitter.model.receipt.Receipt;
import seedu.eylah.expensesplitter.model.receipt.UniqueReceiptList;

/**
 * Wraps all data at the receipt level
 * Duplicates are not allowed (by .isSameReceipt comparison)
 */
public class ReceiptBook implements ReadOnlyReceiptBook {

    private final UniqueReceiptList receipts;

    {
        receipts = new UniqueReceiptList();
    }

    public ReceiptBook() {}

    public ReceiptBook(ReadOnlyReceiptBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Receipt> receipts) {
        this.receipts.setReceipts(receipts);
    }

    /**
     * Resets the existing data of this {@code PersonAmountBook} with {@code newData}.
     */
    public void resetData(ReadOnlyReceiptBook newData) {
        requireNonNull(newData);

        setPersons(newData.getReceiptList());
    }

    @Override
    public ObservableList<Receipt> getReceiptList() {
        return null;
    }
}
