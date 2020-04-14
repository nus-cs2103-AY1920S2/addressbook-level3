package fithelper.model.entry;

import static fithelper.testutil.AssertUtil.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        // null time
        assertFalse(Time.isValidTime(null));

        // invalid time
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("tmr")); // date only
        assertFalse(Time.isValidTime("01:00")); // time only
        assertFalse(Time.isValidTime("tmr 2pm")); // time not in HH:mm format
        assertFalse(Time.isValidTime("tmr-12:20")); // time not in HH:mm format
        assertFalse(Time.isValidTime("2020-04-01 12:20")); // date and time not separated by space

        // valid time
        assertTrue(Time.isValidTime("2020-04-20-23:00"));
        assertTrue(Time.isValidTime("2020-03-23-12:00"));
    }
}
