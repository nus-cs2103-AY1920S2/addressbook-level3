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
     * Replaces the contents of the receipt list with {@code receipts}.
     * {@code receipts} must not contain duplicate receipts.
     */
    public void setReceipts(List<Receipt> receipts) {
        this.receipts.setReceipts(receipts);
    }

    /**
     * Resets the existing data of this {@code ReceiptBook} with {@code newData}.
     */
    public void resetData(ReadOnlyReceiptBook newData) {
        requireNonNull(newData);
        setReceipts(newData.getReceiptList());
    }

    // receipt-level operations

    /**
     * Returns true if a receipt with the same identity as {@code receipt} exists in the receipt book.
     */
    public boolean hasReceipt(Receipt receipt) {
        requireNonNull(receipt);
        return receipts.contains(receipt);
    }

    /**
     * Adds a receipt to the receipt book.
     * The receipt must not already exist in the receipt book.
     */
    public void addReceipt(Receipt r) {
        receipts.add(r);
    }

    @Override
    public ObservableList<Receipt> getReceiptList() {
        return receipts.asUnmodifiableObservableList();
    }

    @Override
    public boolean isContainSingleReceipt() {
        return receipts.isContainSingleReceipt();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReceiptBook // instanceof handles nulls
                && receipts.equals(((ReceiptBook) other).receipts));
    }
}
