package seedu.address.model.util;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.model.group.Group;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Name;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeList;

public class TimeTest {

    public static final String MINUTES_RANGE_ERROR = "Minutes must be within 0-59 (inclusive).";
    public static final String HOURS_RANGE_ERROR = "Hours must be 0 or more";

    @Test
    public void timeCreationValid() {
        Time time = new Time(59, 2);

        Assertions.assertTrue(time.getHours() == 2 && time.getMinutes() == 59);
    }

    @Test
    public void timeCreationInvalid_minutes() {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, (
        ) -> new Time(162, 2));
        Assertions.assertEquals(exception.getMessage(), MINUTES_RANGE_ERROR);
    }

    @Test
    public void timeCreationInvalid_hours() {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, (
        ) -> new Time(16, -5));
        Assertions.assertEquals(exception.getMessage(), HOURS_RANGE_ERROR);
    }

    @Test
    public void timeEqualsFalse() {
        Time time1 = new Time(59, 2);
        Time time2 = new Time(0, 0);
        Assertions.assertFalse(time1.equals(time2));
    }

    @Test
    public void timeAnotherObjectEqualsFalse() {
        Time time = new Time(50, 2);
        Group group = new Group(new Name("Dummy"), new PlaceList(new ArrayList<String>()),
                new ActivityList(new ArrayList<String>()), new TimeList(new ArrayList<String>()));
        Assertions.assertFalse(time.equals(group));
    }

    @Test
    public void timeHashCodeDifferent() {
        Time time1 = new Time(59, 0);
        Time time2 = new Time(1, 0);
        Assertions.assertFalse(time1.hashCode() == time2.hashCode());
    }

    @Test
    public void timeToString() {
        Time time = new Time(59, 2);
        Assertions.assertEquals("2h 59m", time.toString());
    }

    @Test
    public void timeAddTime_changeHour_success() {
        Time time = new Time(59, 2);
        time.addTime(1, 0);
        Time time1 = new Time(0, 3);
        Assertions.assertEquals(time1, time);
    }

    @Test
    public void timeAddTime_noChangeHour_success() {
        Time time = new Time(25, 1);
        time.addTime(25, 2);
        Time time2 = new Time(50, 3);
        Assertions.assertEquals(time, time2);
    }

    @Test
    public void setMinutesRangeError() {
        Time time = new Time(0, 0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            time.setMinutes(120);
        });
    }

    @Test
    public void setMinutesSuccessfully() {
        Time time = new Time(1, 0);
        time.setMinutes(2);
        Assertions.assertEquals(2, time.getMinutes());
    }

    @Test
    public void setHoursRangeSuccessfully() {
        Time time = new Time(1, 0);
        time.setHours(1);
        Assertions.assertEquals(1, time.getHours());
    }

    @Test
    public void setHoursRangeError() {
        Time time = new Time(30, 0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            time.setHours(-1);
        });
    }

}
