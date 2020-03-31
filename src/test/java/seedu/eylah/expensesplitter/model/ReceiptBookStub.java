package seedu.eylah.expensesplitter.model;

import javafx.collections.ObservableList;
import seedu.eylah.expensesplitter.model.receipt.Receipt;
import seedu.eylah.expensesplitter.model.receipt.UniqueReceiptList;

/**
 * A stub class for ReceiptBook.
 */
public class ReceiptBookStub implements ReadOnlyReceiptBook {

    private final UniqueReceiptList receipts;

    {
        receipts = new UniqueReceiptList();
    }

    public ReceiptBookStub() {}

    public ReceiptBookStub(ReadOnlyReceiptBook toBeCopied) {
        this();
    }

    @Override
    public ObservableList<Receipt> getReceiptList() {
        return receipts.asUnmodifiableObservableList();
    }

    @Override
    public boolean isContainSingleReceipt() {
        return receipts.isContainSingleReceipt();
    }


}
