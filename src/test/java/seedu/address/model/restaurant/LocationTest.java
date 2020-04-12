package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        String invalidLoc = "";
        assertThrows(IllegalArgumentException.class, () -> new Location(invalidLoc));
    }

    @Test
    public void isValidLocation() {
        // null location
        assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // invalid location
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation("^")); // only non-alphanumeric characters
        assertFalse(Location.isValidLocation("dasd*")); // contains non-alphanumeric characters

        // valid location
        assertTrue(Location.isValidLocation("afsa sdads")); // alphabets only
        assertTrue(Location.isValidLocation("12345")); // numbers only
        assertTrue(Location.isValidLocation("pfas3 33as")); // alphanumeric characters
        assertTrue(Location.isValidLocation("s sZSW sZ")); // with capital letters
        assertTrue(Location.isValidLocation("sdaZ fs C 3 ")); // long locations
    }
}
