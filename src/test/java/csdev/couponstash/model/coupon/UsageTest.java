package csdev.couponstash.model.coupon;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UsageTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Usage(null));
    }

    @Test
    public void constructor_invalidUsage_throwsIllegalArgumentException() {
        String invalidUsage = "-1";
        assertThrows(IllegalArgumentException.class, () -> new Usage(invalidUsage));
    }

    @Test
    public void isValidUsage() {
        // null usage
        assertThrows(NullPointerException.class, () -> Limit.isValidLimit(null));

        // invalid usage
        assertFalse(Usage.isValidUsage("-1")); // Negative number
        assertFalse(Usage.isValidUsage("" + (Integer.MAX_VALUE + 1))); // Maximum value of Integer.MAX_VALUE
        assertFalse(Usage.isValidUsage(" ")); // spaces only
        assertFalse(Usage.isValidUsage("")); // empty string
        assertFalse(Usage.isValidUsage("^")); // only non-alphanumeric characters
        assertFalse(Usage.isValidUsage("peter*")); // contains non-alphanumeric characters

        // valid usage
        assertTrue(Usage.isValidUsage("" + Integer.MAX_VALUE)); // Maximum value of Integer.MAX_VALUE
        assertTrue(Usage.isValidUsage("0")); // Minimum value of 0
    }
}
