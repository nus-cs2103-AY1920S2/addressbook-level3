package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.ModelStub;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;
import seedu.eylah.expensesplitter.testutil.TypicalEntries;


public class DoneReceiptCommandTest {

    private static final Entry ENTRY_ONE = TypicalEntries.ENTRY_ONE;

    @Test
    public void execute_receiptIsAlreadyDone_markAsDoneUnsuccessful() {
        ModelStubReceiptToBeMadeDone modelStub = new ModelStubReceiptToBeMadeDone();
        //add an Entry to simulate user adding an item
        modelStub.addEntry(ENTRY_ONE);
        modelStub.getReceipt().makeDone();

        CommandResult commandResult = new DoneReceiptCommand().execute(modelStub);

        // confirm that receipt is marked as done
        assertTrue(modelStub.getReceipt().isDone());

        assertEquals(DoneReceiptCommand.MESSAGE_RECEIPT_DONE, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_receiptMadeDone_markAsDoneSuccessful() {
        ModelStubReceiptToBeMadeDone modelStub = new ModelStubReceiptToBeMadeDone();
        //add an Entry to simulate user adding an item
        modelStub.addEntry(ENTRY_ONE);

        CommandResult commandResult = new DoneReceiptCommand().execute(modelStub);

        // confirm that receipt is marked as done
        assertTrue(modelStub.getReceipt().isDone());

        assertEquals(DoneReceiptCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }

    /**
     * This model stub accepts adding of entries and deleting of entries, so as the mark Receipt
     * as done.
     */
    private class ModelStubReceiptToBeMadeDone extends ModelStub {
        final Receipt receipt = new Receipt();

        @Override
        public boolean isReceiptDone() {
            return receipt.isDone();
        }

        @Override
        public Receipt getReceipt() {
            return this.receipt;
        }

        @Override
        public void addEntry(Entry entry) {
            requireNonNull(entry);
            this.receipt.addEntry(entry);
        }

        @Override
        public String listReceipt() {
            return receipt.toString();
        }
    }
}
