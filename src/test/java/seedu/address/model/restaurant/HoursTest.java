package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HoursTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Hours(null));
    }

    @Test
    public void isValidHours() {
        // null hours
        assertThrows(NullPointerException.class, () -> Hours.isValidHours(null));

        // invalid hours
        assertFalse(Hours.isValidHours(" ")); // spaces only
        assertFalse(Hours.isValidHours("^")); // only non-alphanumeric characters
        assertFalse(Hours.isValidHours("dasd*")); // contains non-alphanumeric characters
        assertFalse(Hours.isValidHours("afsa sdads")); // alphabets only
        assertFalse(Hours.isValidHours("12345")); // numbers only
        assertFalse(Hours.isValidHours("pfas3 33as")); // alphanumeric characters
        assertFalse(Hours.isValidHours("s sZSW sZ")); // with capital letters
        assertFalse(Hours.isValidHours("sdaZ fs C 3 ")); // long hourss
        assertFalse(Hours.isValidHours("$$")); // long hourss
        assertFalse(Hours.isValidHours("0000:2400"));
        assertFalse(Hours.isValidHours("0000:24003"));
        assertFalse(Hours.isValidHours("0523:2360"));

        // valid hours
        assertTrue(Hours.isValidHours("")); // empty string
        assertTrue(Hours.isValidHours("1500:1400"));
        assertTrue(Hours.isValidHours("0000:2359"));
    }
}
