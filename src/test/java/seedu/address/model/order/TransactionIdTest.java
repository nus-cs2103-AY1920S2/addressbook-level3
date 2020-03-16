package seedu.address.model.order;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class TransactionIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionId(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidTID = "";
        assertThrows(IllegalArgumentException.class, () -> new TransactionId(invalidTID));
    }

    @Test
    public void isValidTID() {
        // null TID
        assertThrows(NullPointerException.class, () -> TransactionId.isValidTId(null));

        // invalid TID
        assertFalse(TransactionId.isValidTId("")); // empty string
        assertFalse(TransactionId.isValidTId("   ")); // string with spaces

        // valid TID
        assertTrue(TransactionId.isValidTId("AAAAAAAAAA")); // alphabets only
        assertTrue(TransactionId.isValidTId("12345678")); // numbers only
        assertTrue(TransactionId.isValidTId("A943739")); // alphanumeric characters
        assertTrue(TransactionId.isValidTId("A01010101010101010101")); // long TID
    }
}
