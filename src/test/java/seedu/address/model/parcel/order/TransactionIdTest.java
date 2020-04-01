package seedu.address.model.parcel.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.parcel.parcelattributes.TransactionId;

public class TransactionIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionId(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidTid = "";
        assertThrows(IllegalArgumentException.class, () -> new TransactionId(invalidTid));
    }

    @Test
    public void isValidTid() {
        // null TID
        assertThrows(NullPointerException.class, () -> TransactionId.isValidTid(null));

        // invalid TID
        assertFalse(TransactionId.isValidTid("")); // empty string
        assertFalse(TransactionId.isValidTid("   ")); // string with spaces

        // valid TID
        assertTrue(TransactionId.isValidTid("AAAAAAAAAA")); // alphabets only
        assertTrue(TransactionId.isValidTid("12345678")); // numbers only
        assertTrue(TransactionId.isValidTid("A943739")); // alphanumeric characters
        assertTrue(TransactionId.isValidTid("A01010101010101010101")); // long TID
    }
}
