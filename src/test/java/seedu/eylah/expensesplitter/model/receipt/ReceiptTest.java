package seedu.eylah.expensesplitter.model.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.expensesplitter.testutil.TypicalEntries.ENTRY_ONE;
import static seedu.eylah.expensesplitter.testutil.TypicalEntries.ENTRY_TWO;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.Test;

public class ReceiptTest {

    private final Receipt receipt = new Receipt();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), receipt.getReceipt());
    }

    @Test
    public void hasEntry_entryNotInReceipt_returnsFalse() {
        assertFalse(receipt.hasEntry(ENTRY_ONE));
    }

    @Test
    public void hasEntry_entryInReceipt_returnsTrue() {
        receipt.addEntry(ENTRY_ONE);
        assertTrue(receipt.hasEntry(ENTRY_ONE));
    }

    @Test
    public void hasEntry_receiptHasMultipleEntries_returnsFalse() {
        receipt.addEntry(ENTRY_ONE);
        receipt.addEntry(ENTRY_TWO);
        assertTrue(receipt.hasEntry(ENTRY_ONE));
        assertTrue(receipt.hasEntry(ENTRY_TWO));
    }

    @Test
    public void deleteEntry_noEntry_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> receipt.deleteEntry(0));
    }

    @Test
    public void deleteEntry_deleteNonExistentIndex_throwsIndexOutOfBoundsException() {
        receipt.addEntry(ENTRY_ONE);
        assertThrows(IndexOutOfBoundsException.class, () -> receipt.deleteEntry(1));
    }

    @Test
    public void isDone_checkIfReceiptInitialisedAsUndone_returnsFalse() {
        assertFalse(receipt.isDone());
    }

    @Test
    public void isDone_checkIfReceiptMarkAsDone_returnsTrue() {
        receipt.makeDone();
        assertTrue(receipt.isDone());
    }

    @Test
    public void isDone_checkIfReceiptIsUndoneAfterClear_returnsFalse() {
        receipt.addEntry(ENTRY_ONE);
        receipt.makeDone();
        receipt.clearReceipt();
        assertFalse(receipt.isDone());
    }

    @Test
    public void clearReceipt_checkIfReceiptContainsEntryAfterClearing_returnsFalse() {
        receipt.addEntry(ENTRY_ONE);
        receipt.clearReceipt();
        assertFalse(receipt.hasEntry(ENTRY_ONE));
    }
}
