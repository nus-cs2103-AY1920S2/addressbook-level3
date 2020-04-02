package csdev.couponstash.commons.util;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

    @Test
    public void isValidDate() {
        // null Expiry Date
        assertThrows(NullPointerException.class, () -> DateUtil.isValidDate(null));

        // invalid expiry dates
        assertFalse(DateUtil.isValidDate("")); // empty string
        assertFalse(DateUtil.isValidDate(" ")); // spaces only
        assertFalse(DateUtil.isValidDate("1-1-11")); // only 2 numbers for yyyy
        assertFalse(DateUtil.isValidDate("date")); // non-numeric
        assertFalse(DateUtil.isValidDate("1 - 30 - 2020")); // spaces within digits

        // valid expiry dates
        assertTrue(DateUtil.isValidDate("01-08-2020"));
        assertTrue(DateUtil.isValidDate("1-8-2020"));
    }
}
