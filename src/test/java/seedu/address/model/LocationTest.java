package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Location;

public class LocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidLocation = "";
        assertThrows(IllegalArgumentException.class, () -> new Location(invalidLocation));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // invalid name
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation("^")); // only non-alphanumeric characters
        assertFalse(Location.isValidLocation("home*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Location.isValidLocation("my house")); // alphabets only
        assertTrue(Location.isValidLocation("12345")); // numbers only
        assertTrue(Location.isValidLocation("2nd floor of com1")); // alphanumeric characters
        assertTrue(Location.isValidLocation("COM1")); // with capital letters
        assertTrue(Location.isValidLocation("NUS School of Computing Level 1")); // long names
    }
}
