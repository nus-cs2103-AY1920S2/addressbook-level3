package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.ModelStub;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;
import seedu.eylah.expensesplitter.testutil.TypicalEntries;


public class ClearReceiptCommandTest {

    private static final Entry ENTRY_ONE = TypicalEntries.ENTRY_ONE;
    private static final Entry ENTRY_TWO = TypicalEntries.ENTRY_TWO;
    private static final Entry ENTRY_THREE = TypicalEntries.ENTRY_THREE;

    @Test
    public void execute_clearReceipt_successful() {
        ModelStubPopulatedReceipt modelStub = new ModelStubPopulatedReceipt();
        modelStub.addEntry(ENTRY_ONE);
        modelStub.addEntry(ENTRY_TWO);
        modelStub.addEntry(ENTRY_THREE);
        modelStub.getReceipt().makeDone();

        CommandResult commandResult = new ClearReceiptCommand().execute(modelStub);

        // confirm that the Receipt is empty.
        assertTrue(modelStub.getReceipt().getReceipt().isEmpty());

        assertEquals(ClearReceiptCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_makeReceiptUndone_successful() {
        ModelStubPopulatedReceipt modelStub = new ModelStubPopulatedReceipt();
        modelStub.addEntry(ENTRY_ONE);
        modelStub.addEntry(ENTRY_TWO);
        modelStub.addEntry(ENTRY_THREE);
        modelStub.getReceipt().makeDone();

        CommandResult commandResult = new ClearReceiptCommand().execute(modelStub);

        // confirm that the Receipt is made undone.
        assertFalse(modelStub.getReceipt().isDone());

        assertEquals(ClearReceiptCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }

    /**
     * This model stub accepts adding of entries and deleting of entries, so as to populate the
     * receipt with entries.
     */
    private class ModelStubPopulatedReceipt extends ModelStub {
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
        public void clearReceipt() {
            receipt.clearReceipt();
        }

        @Override
        public void addEntry(Entry entry) {
            requireNonNull(entry);
            this.receipt.addEntry(entry);
        }

    }
}
