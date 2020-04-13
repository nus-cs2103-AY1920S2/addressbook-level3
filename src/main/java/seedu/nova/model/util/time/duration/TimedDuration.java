package seedu.nova.model.util.time.duration;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import seedu.nova.model.util.Copyable;

/**
 * Combination of duration with some time data structure
 */
public interface TimedDuration extends Comparable<TimedDuration>, Copyable<TimedDuration>, Serializable {
    static int getDayTimeValue(DayOfWeek dow, LocalTime lt) {
        return 86400 * dow.getValue() + (int) (lt.toNanoOfDay() / 1000000000);
    }

    boolean isZero();

    DayOfWeek getStartDay();

    DayOfWeek getEndDay();

    LocalTime getStartTime();

    LocalTime getEndTime();

    Duration getDuration();

    boolean isConnected(TimedDuration another); // isOverlapping && !isSubsetOf

    boolean isOverlapping(TimedDuration another);

    boolean isSubsetOf(TimedDuration another);

    List<TimedDuration> relativeComplementOf(TimedDuration another);

    TimedDuration intersectWith(TimedDuration another);
}
