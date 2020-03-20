package csdev.couponstash.model.coupon;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StartDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StartDate(null));
    }

    @Test
    public void constructor_invalidStartDate_throwsIllegalArgumentException() {
        String invalidStartDate = " ";
        assertThrows(IllegalArgumentException.class, () -> new StartDate(invalidStartDate));
    }

    @Test
    public void isValidStartDate() {
        // null Start Date
        assertThrows(NullPointerException.class, () -> StartDate.isValidStartDate(null));

        // invalid start dates
        assertFalse(StartDate.isValidStartDate(" ")); // spaces only
        assertFalse(StartDate.isValidStartDate("1-1-11")); // only 2 numbers for yyyy
        assertFalse(StartDate.isValidStartDate("date")); // non-numeric
        assertFalse(StartDate.isValidStartDate("1 - 30 - 2020")); // spaces within digits

        // valid start dates
        assertTrue(StartDate.isValidStartDate("")); // empty string
        assertTrue(StartDate.isValidStartDate("01-08-2020")); // exactly 3 numbers
        assertTrue(StartDate.isValidStartDate("1-8-2020"));
    }
}
