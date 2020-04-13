package seedu.eylah.expensesplitter.testutil;

import java.util.ArrayList;

import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;

/**
 * A utility class to help with building Receipt objects.
 */
public class ReceiptBuilder {

    private static final ArrayList<Entry> DEFAULT_RECEIPT = TypicalEntries.getTypicalEntries();
    private static final boolean DEFAULT_ISDONE = false;

    private ArrayList<Entry> receipt;
    private boolean isDone;

    public ReceiptBuilder() {
        receipt = DEFAULT_RECEIPT;
        isDone = DEFAULT_ISDONE;
    }

    public ReceiptBuilder(Receipt receiptToCopy) {
        receipt = receiptToCopy.getReceipt();
        isDone = receiptToCopy.isDone();
    }

    /**
     * Sets the {@code receipt} of the {@code Receipt} that we are building.
     */
    public ReceiptBuilder withReceipt(ArrayList<Entry> receipt) {
        this.receipt = receipt;
        return this;
    }

    /**
     * Sets the {@code isDone} of the {@code Receipt} that we are building.
     */
    public ReceiptBuilder withIsDone(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    public Receipt build() {
        return new Receipt(receipt, isDone);
    }

}
