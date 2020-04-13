package seedu.eylah.expensesplitter.model;

import seedu.eylah.expensesplitter.model.receipt.Receipt;

/**
 * This simple ReceiptStub resembles Receipt in Expense Splitter but has limited functionality to ensure that
 * that there is no error.
 *
 * Used in :
 * PaidCommandTest
 *
 */
public class ReceiptStub extends Receipt {

    @Override
    public void makeDone() {
        super.makeDone();
    }

    @Override
    public boolean isDone() {
        return super.isDone();
    }
}
