package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime((String) null));
    }

    @Test
    public void constructor_invalidDateTimeString_throwsArgumentException() {
        //invalid format.
        assertThrows(IllegalArgumentException.class, () -> new DateTime(""));

        //invalid month value.
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-13-02 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-00-02 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-20-02 10:00"));

        //invalid date value.
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2019-02-29 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-30 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-04-31 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-03-32 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-03-00 10:00"));

        //invalid time value.
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-20 12:60"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-20 12:61"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-20 25:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-20 30:00"));
    }

    @Test
    public void isValidDateTime() {
        //null date time
        assertThrows(NullPointerException.class, () -> DateTime.isValidDateTime(null));

        //invalid date time string
        assertThrows(IllegalArgumentException.class, () -> new DateTime("")); // empty string
        assertThrows(IllegalArgumentException.class, () -> new DateTime(" ")); // space only
        assertThrows(IllegalArgumentException.class, () -> new DateTime("201")); // invalid year format
        assertThrows(IllegalArgumentException.class, () -> new DateTime("12345")); // invalid year format
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2012")); // year only
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-02")); // missing time value
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-0210:00")); // missing space
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-02 10")); // missing minute value
        assertThrows(IllegalArgumentException.class, () -> new DateTime("02-02-2020 10:00")); // wrong date format
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020/02/02 10:00")); // wrong date format

        //valid date time string
        assertTrue(DateTime.isValidDateTime("2020-02-20 10:00"));
        assertTrue(DateTime.isValidDateTime("2020-02-28 20:00"));
        assertTrue(DateTime.isValidDateTime("2020-03-15 23:59"));
    }
}
