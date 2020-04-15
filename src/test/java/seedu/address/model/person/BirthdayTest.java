package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BirthdayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidBirthday_throwsIllegalArgumentException() {
        String invalidBirthday = "31-05";
        assertThrows(IllegalArgumentException.class, () -> new Birthday(invalidBirthday));
    }

    @Test
    public void isValidDate() {
        // null birthday
        assertThrows(NullPointerException.class, () -> Birthday.isValidDate(null));

        // invalid birthday
        assertFalse(Birthday.isValidDate("31-05")); // Wrong date format

        // valid birthday
        assertTrue(Birthday.isValidDate("05-31"));
    }
}
