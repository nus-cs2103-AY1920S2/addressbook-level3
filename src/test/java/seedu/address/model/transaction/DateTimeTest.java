package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.transaction.TypicalDateTimes.MARCH_FIRST_2020_10AM;
import static seedu.address.testutil.transaction.TypicalDateTimes.MARCH_FIRST_2020_5PM;
import static seedu.address.testutil.transaction.TypicalDateTimes.MARCH_SECOND_2020_5PM;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Singapore"));
        LocalDateTime currentPlusOneHour = currentDateTime.plusHours(1);
        LocalDateTime currentPlusOneDay = currentDateTime.plusDays(1);


        //null date time
        assertThrows(NullPointerException.class, () -> DateTime.isValidDateTime(null));

        //invalid date time string
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // space only
        assertFalse(DateTime.isValidDateTime("201")); // invalid year format
        assertFalse(DateTime.isValidDateTime("12345")); // invalid year format
        assertFalse(DateTime.isValidDateTime("2012")); // year only
        assertFalse(DateTime.isValidDateTime("2020-02-02")); // missing time value
        assertFalse(DateTime.isValidDateTime("2020-02-0210:00")); // missing space
        assertFalse(DateTime.isValidDateTime("2020-02-02 10")); // missing minute value
        assertFalse(DateTime.isValidDateTime("02-02-2020 10:00")); // wrong date format
        assertFalse(DateTime.isValidDateTime("2020/02/02 10:00")); // wrong date format
        assertFalse(DateTime.isValidDateTime(currentPlusOneHour.format(formatter))); // in future by one hour
        assertFalse(DateTime.isValidDateTime(currentPlusOneDay.format(formatter))); // in future by one day

        //valid date time string
        assertTrue(DateTime.isValidDateTime("2020-02-20 10:00"));
        assertTrue(DateTime.isValidDateTime("2020-02-28 20:00"));
        assertTrue(DateTime.isValidDateTime("2020-03-15 23:59"));
    }

    @Test
    public void isBefore() {
        //one's day before another's day -> returns true
        assertTrue(MARCH_FIRST_2020_5PM.isBefore(MARCH_SECOND_2020_5PM));

        //one's hour before another's -> returns true
        assertTrue(MARCH_FIRST_2020_10AM.isBefore(MARCH_FIRST_2020_5PM));

        //compared with null -> throw null pointer exception
        assertThrows(NullPointerException.class, () -> MARCH_FIRST_2020_5PM.isBefore(null));
    }

    @Test
    public void isAfter() {
        //one's day after another's day -> return true
        assertTrue(MARCH_SECOND_2020_5PM.isAfter(MARCH_FIRST_2020_5PM));

        //one's hour after another's hour -> return true
        assertTrue(MARCH_FIRST_2020_5PM.isAfter(MARCH_FIRST_2020_10AM));

        //compared with null -> throw null pointer exception
        assertThrows(NullPointerException.class, () -> MARCH_FIRST_2020_5PM.isAfter(null));
    }

    @Test
    public void isOnSameDay() {
        //two DateTime objects on the same day but different time -> returns true
        assertTrue(MARCH_FIRST_2020_5PM.isOnSameDay(MARCH_FIRST_2020_10AM));

        //two identical datetime objects -> returns true
        assertTrue(MARCH_FIRST_2020_5PM.isOnSameDay(MARCH_FIRST_2020_5PM));

        //tested with null -> throws null pointer exception
        assertThrows(NullPointerException.class, () -> MARCH_FIRST_2020_5PM.isOnSameDay(null));
    }
}
