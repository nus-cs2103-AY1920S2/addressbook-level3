package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidAddress));
    }

    @Test
    public void isValidDate() {
        // null address
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid addresses
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // valid addresses
        assertTrue(Date.isValidDate("Blk 456, Den Road, #01-355"));
        assertTrue(Date.isValidDate("-")); // one character
        assertTrue(Date.isValidDate("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
