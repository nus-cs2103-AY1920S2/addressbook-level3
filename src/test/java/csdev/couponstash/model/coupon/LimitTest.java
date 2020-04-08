package csdev.couponstash.model.coupon;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LimitTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Limit(null));
    }

    @Test
    public void constructor_invalidLimit_throwsIllegalArgumentException() {
        String invalidLimit = "asdf";
        assertThrows(IllegalArgumentException.class, () -> new Limit(invalidLimit));
    }

    @Test
    public void isValidLimit() {
        // null limit
        assertThrows(NullPointerException.class, () -> Limit.isValidLimit(null));

        // invalid limit
        assertFalse(Limit.isValidLimit("" + (Integer.MIN_VALUE - 1.0))); // Integer.MIN_VALUE - 1
        assertFalse(Limit.isValidLimit("" + (Integer.MAX_VALUE + 1.0))); // Integer.MAX_VALUE + 1
        assertFalse(Limit.isValidLimit("")); // empty string
        assertFalse(Limit.isValidLimit(" ")); // spaces only
        assertFalse(Limit.isValidLimit("peter*1234")); // string

        // valid limit
        assertTrue(Limit.isValidLimit("" + Integer.MAX_VALUE)); // max value
        assertTrue(Limit.isValidLimit("" + Integer.MIN_VALUE)); // min value
        assertTrue(Limit.isValidLimit("0")); // zero
    }
}
