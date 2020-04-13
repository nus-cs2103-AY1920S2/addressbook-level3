package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_SUNNYDAY;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.exceptions.InvalidDayAndTimeException;
import seedu.address.model.lesson.exceptions.InvalidTimeRangeException;

public class DayAndTimeTest {

    @Test
    public void constructor_null_throwsException() {
        assertThrows(NullPointerException.class, () -> new DayAndTime(null));
    }

    @Test
    public void constructor_invalidTimeRange_throwsException() {
        assertThrows(InvalidTimeRangeException.class, () -> new DayAndTime("SUNDAY 12:00 11:00"));
        assertThrows(InvalidTimeRangeException.class, () -> new DayAndTime(DayOfWeek.SUNDAY, LocalTime.parse("12:00"),
                LocalTime.parse("11:00")));
    }

    @Test
    public void constructor_invalidDay_throwsException() {
        assertThrows(InvalidDayAndTimeException.class, () -> new DayAndTime(INVALID_DAY_SUNNYDAY));
    }

    @Test
    public void constructor_invalidTime_throwsException() {
        // invalid start time
        assertThrows(InvalidDayAndTimeException.class, () -> new DayAndTime("SUNDAY 2:00 11:00"));

        // invalid end time
        assertThrows(InvalidDayAndTimeException.class, () -> new DayAndTime("SUNDAY 12:00 1:00"));

        // invalid start time and end time
        assertThrows(InvalidDayAndTimeException.class, () -> new DayAndTime("SUNDAY 2:00 1:00"));
    }

    @Test
    public void equals() {
        DayAndTime dayAndTime = new DayAndTime(DayOfWeek.WEDNESDAY, LocalTime.parse("01:00"), LocalTime.parse("02:00"));
        DayAndTime anotherDayAndTime = new DayAndTime(DayOfWeek.MONDAY,
                LocalTime.parse("01:00"), LocalTime.parse("02:00"));

        // same object should return true
        assertTrue(dayAndTime.equals(dayAndTime));

        // same values should return true
        DayAndTime another = new DayAndTime(DayOfWeek.WEDNESDAY, LocalTime.parse("01:00"), LocalTime.parse("02:00"));
        assertTrue(dayAndTime.equals(another));

        // null should return false
        assertFalse(dayAndTime.equals(null));

        // different type should return false
        assertFalse(dayAndTime.equals(1));

        // different day should return false
        assertFalse(dayAndTime.equals(anotherDayAndTime));

        // different start time should return false
        anotherDayAndTime = new DayAndTime(DayOfWeek.WEDNESDAY, LocalTime.parse("01:30"), LocalTime.parse("02:00"));
        assertFalse(dayAndTime.equals(anotherDayAndTime));

        // different end time should return false
        anotherDayAndTime = new DayAndTime(DayOfWeek.WEDNESDAY, LocalTime.parse("01:00"), LocalTime.parse("03:00"));
        assertFalse(dayAndTime.equals(anotherDayAndTime));

        // different day and time should return false
        anotherDayAndTime = new DayAndTime(DayOfWeek.SUNDAY, LocalTime.parse("04:00"), LocalTime.parse("05:00"));
        assertFalse(dayAndTime.equals(anotherDayAndTime));
    }
}
