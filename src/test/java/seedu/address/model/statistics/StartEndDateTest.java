package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StartEndDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StartEndDate(null));
    }

    @Test
    public void constructor_invalidStartEndDate_throwsIllegalArgumentException() {
        String invalidStartEndDate = "";
        assertThrows(IllegalArgumentException.class, () -> new StartEndDate(invalidStartEndDate));
    }

    @Test
    public void isValidStartEndDate() {
        // null phone number
        assertThrows(NullPointerException.class, () -> StartEndDate.isValidStartEndDate(null));

        // invalid phone numbers
        assertFalse(StartEndDate.isValidStartEndDate("")); // empty string
        assertFalse(StartEndDate.isValidStartEndDate(" ")); // spaces only
        assertFalse(StartEndDate.isValidStartEndDate("price")); // non-numeric
        assertFalse(StartEndDate.isValidStartEndDate("9011p041")); // alphabets within digits
        assertFalse(StartEndDate.isValidStartEndDate("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(StartEndDate.isValidStartEndDate("2020-01-01")); // exactly 3 numbers
        assertTrue(StartEndDate.isValidStartEndDate("2020-12-12"));
        assertTrue(StartEndDate.isValidStartEndDate("2020-01-12")); // long prices
    }
}
