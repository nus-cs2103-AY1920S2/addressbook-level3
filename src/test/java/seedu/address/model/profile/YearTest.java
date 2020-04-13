package seedu.address.model.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author joycelynteo

public class YearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void isValidCode() {
        // null input
        assertThrows(NullPointerException.class, () -> Year.isValidCode(null));

        // invalid year
        assertFalse(Year.isValidCode("0.1")); // lower boundary of year
        assertFalse(Year.isValidCode("10.1")); // upper boundary of year
        assertFalse(Year.isValidCode("5.0")); // lower boundary of semester
        assertFalse(Year.isValidCode("5.3")); // upper boundary of semester

        // valid year
        assertTrue(Year.isValidCode("1.1")); // lower boundary of yea
        assertTrue(Year.isValidCode("9.1")); // upper boundary of year
        assertTrue(Year.isValidCode("5.1")); // lower boundary of semester
        assertTrue(Year.isValidCode("5.2")); // upper boundary of semester
    }

    @Test
    public void getSemester() {
        Year year4 = new Year("2.2");
        assertEquals(year4.getSemester(), 4);

        Year year7 = new Year("4.1");
        assertEquals(year7.getSemester(), 7);
    }

    @Test
    public void equals() {
        Year year = new Year("1.1");
        Year yearCopy = new Year("1.1");

        // object is null -> return false
        assertFalse(year.equals(null));

        // different semester -> return false
        assertFalse(year.equals(new Year("1.2")));

        // different year -> return false
        assertFalse(year.equals(new Year("2.1")));

        // different type -> return false
        assertFalse(year.equals(1.1));

        // same object -> return true
        assertTrue(year.equals(year));

        // same values -> return true
        assertTrue(year.equals(yearCopy));
    }

}
