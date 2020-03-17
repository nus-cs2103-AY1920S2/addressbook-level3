package seedu.zerotoone.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IntervalTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Interval(null));
    }

    @Test
    public void constructor_invalidInterval_throwsIllegalArgumentException() {
        String invalidInterval = "";
        assertThrows(IllegalArgumentException.class, () -> new Interval(invalidInterval));
    }

    @Test
    public void isValidInterval() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Interval.isValidInterval(null));

        // invalid phone numbers
        assertFalse(Interval.isValidInterval("")); // empty string
        assertFalse(Interval.isValidInterval(" ")); // spaces only
        assertFalse(Interval.isValidInterval("phone")); // non-numeric
        assertFalse(Interval.isValidInterval("9011p041")); // alphabets within digits
        assertFalse(Interval.isValidInterval("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Interval.isValidInterval("911"));
        assertTrue(Interval.isValidInterval("93121534"));
        assertTrue(Interval.isValidInterval("124293842033123")); // long numbers
    }
}
