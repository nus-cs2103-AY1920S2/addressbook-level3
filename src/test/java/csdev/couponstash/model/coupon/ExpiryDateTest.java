package csdev.couponstash.model.coupon;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExpiryDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExpiryDate(null));
    }

    @Test
    public void constructor_invalidExpiryDate_throwsIllegalArgumentException() {
        String invalidExpiryDate = "";
        assertThrows(IllegalArgumentException.class, () -> new ExpiryDate(invalidExpiryDate));
    }

    @Test
    public void isValidExpiryDate() {
        // null Expiry Date
        assertThrows(NullPointerException.class, () -> ExpiryDate.isValidExpiryDate(null));

        // invalid expiry dates
        assertFalse(ExpiryDate.isValidExpiryDate("")); // empty string
        assertFalse(ExpiryDate.isValidExpiryDate(" ")); // spaces only
        assertFalse(ExpiryDate.isValidExpiryDate("1-1-11")); // only 2 numbers for yyyy
        assertFalse(ExpiryDate.isValidExpiryDate("date")); // non-numeric
        assertFalse(ExpiryDate.isValidExpiryDate("1 - 30 - 2020")); // spaces within digits

        // valid expiry dates
        assertTrue(ExpiryDate.isValidExpiryDate("01-08-2020"));
        assertTrue(ExpiryDate.isValidExpiryDate("1-8-2020"));
    }
}
