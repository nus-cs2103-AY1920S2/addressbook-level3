package seedu.address.model.order;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class TransactionIDTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionID(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidTID = "";
        assertThrows(IllegalArgumentException.class, () -> new TransactionID(invalidTID));
    }

    @Test
    public void isValidTID() {
        // null TID
        assertThrows(NullPointerException.class, () -> TransactionID.isValidTID(null));

        // invalid TID
        assertFalse(TransactionID.isValidTID("")); // empty string
        assertFalse(TransactionID.isValidTID("   ")); // string with spaces

        // valid TID
        assertTrue(TransactionID.isValidTID("AAAAAAAAAA")); // alphabets only
        assertTrue(TransactionID.isValidTID("12345678")); // numbers only
        assertTrue(TransactionID.isValidTID("A943739")); // alphanumeric characters
        assertTrue(TransactionID.isValidTID("A01010101010101010101")); // long TID
    }
}
