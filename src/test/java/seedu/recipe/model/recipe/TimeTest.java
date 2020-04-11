package seedu.recipe.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime));
    }

    @Test
    public void isValidTime() {
        // null time number
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid time numbers
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("time")); // non-numeric
        assertFalse(Time.isValidTime("9011p041")); // alphabets within digits
        assertFalse(Time.isValidTime("9312 1534")); // spaces within digits
        assertFalse(Time.isValidTime("95699999954069")); //long numbers
        assertFalse(Time.isValidTime("-20")); //negative numbers
        assertFalse(Time.isValidTime("0")); //zero value
        assertFalse(Time.isValidTime("0000")); //multiple zero value
        assertFalse(Time.isValidTime("11.24")); //decimal

        // valid time numbers
        assertTrue(Time.isValidTime("1")); // exactly 1 number
        assertTrue(Time.isValidTime("91")); // 2 numbers
        assertTrue(Time.isValidTime("911")); // 3 numbers
        assertTrue(Time.isValidTime("2999")); // less than 3000
    }
}
