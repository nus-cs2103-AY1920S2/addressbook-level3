package seedu.nova.model.util.time.duration;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import seedu.nova.model.util.time.TimeUtil;

/**
 * Combining LocalDateTime and Duration
 */
public class DateTimeDuration implements TimedDuration {

    public static final TimedDuration ZERO = new DateTimeDuration(Duration.ZERO);

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Duration duration;

    public DateTimeDuration(LocalDateTime start, LocalDateTime stop) {
        this.startDateTime = start;
        this.endDateTime = stop;
        if (start.compareTo(stop) < 0) {
            this.duration = Duration.between(start, stop);
        } else {
            this.duration = Duration.ZERO;
        }
    }

    public DateTimeDuration(LocalDate date, LocalTime start, LocalTime end) {
        if (start.compareTo(end) < 0) {
            this.startDateTime = LocalDateTime.of(date, start);
            this.endDateTime = LocalDateTime.of(date, end);
            this.duration = Duration.between(this.startDateTime, this.endDateTime);
        } else {
            this.startDateTime = LocalDateTime.of(date, start);
            this.endDateTime = LocalDateTime.of(date.plusDays(1), end);
            this.duration = Duration.between(this.startDateTime, this.endDateTime);
        }
    }

    public DateTimeDuration(LocalDate date, LocalTime start, Duration duration) {
        this.startDateTime = LocalDateTime.of(date, start);
        this.duration = duration;
        this.endDateTime = this.startDateTime.plus(duration);
    }

    private DateTimeDuration(Duration duration) {
        this.duration = duration;
    }

    private DateTimeDuration(LocalDateTime start, LocalDateTime end, Duration duration) {
        this.startDateTime = start;
        this.endDateTime = end;
        this.duration = duration;
    }

    /**
     * DateTimeDuration representation of a duration
     *
     * @param duration
     * @return DateTimeDuration
     */
    public static DateTimeDuration parseDuration(Duration duration) {
        return new DateTimeDuration(duration);
    }

    /**
     * Parse DateTimeDuration of a day
     *
     * @param lDate date of the day
     * @return DateTimeDuration representation of the day
     */
    public static DateTimeDuration parseDayFromDate(LocalDate lDate) {
        return new DateTimeDuration(
                LocalDateTime.of(LocalDate.of(lDate.getYear(), lDate.getMonth(), lDate.getDayOfMonth()),
                        TimeUtil.BEGIN_DAY_TIME),
                LocalDateTime.of(lDate.plusDays(1), TimeUtil.BEGIN_DAY_TIME)
        );
    }

    /**
     * Parse DateTimeDuration
     *
     * @param monDate a day from the week
     * @return DateTimeDuration of the week
     */
    public static DateTimeDuration parseWeekFromDate(LocalDate monDate) {
        return new DateTimeDuration(
                LocalDateTime.of(TimeUtil.getMondayOfWeek(monDate), TimeUtil.BEGIN_DAY_TIME),
                LocalDateTime.of(TimeUtil.getMondayOfWeek(monDate).plusDays(1), TimeUtil.BEGIN_DAY_TIME)
        );
    }

    /**
     * Parse DateTimeDuration
     *
     * @param startDateTime startDateTime
     * @param duration      duration
     * @return DateTimeDuration
     */
    public static DateTimeDuration parseFromDateTime(LocalDateTime startDateTime, Duration duration) {
        return new DateTimeDuration(
                startDateTime,
                startDateTime.plusMinutes(duration.toMinutes())
        );
    }

    public boolean isZero() {
        return this.duration.isZero();
    }

    //-----getters

    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    public LocalDate getStartDate() {
        return this.startDateTime.toLocalDate();
    }

    public void setStartDate(LocalDate date) {
        this.startDateTime = LocalDateTime.of(date, this.startDateTime.toLocalTime());
        this.endDateTime = this.startDateTime.plus(this.duration);
    }

    public LocalTime getStartTime() {
        return this.startDateTime.toLocalTime();
    }

    public DayOfWeek getStartDay() {
        return this.startDateTime.getDayOfWeek();
    }

    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public LocalDate getEndDate() {
        return this.endDateTime.toLocalDate();
    }

    public LocalTime getEndTime() {
        return this.endDateTime.toLocalTime();
    }

    public DayOfWeek getEndDay() {
        return this.endDateTime.getDayOfWeek();
    }

    public Duration getDuration() {
        return this.duration;
    }

    public long toDays() {
        return this.duration.toDays();
    }

    public long toWeeks() {
        return (long) Math.ceil((toDays() + 0.0) / 7);
    }

    public List<LocalDate> getWeekStartList() {
        List<LocalDate> lst = new ArrayList<>();
        LocalDate d = this.startDateTime.toLocalDate();
        LocalDate dd = this.endDateTime.toLocalDate();
        while (d.compareTo(dd) <= 0) {
            lst.add(d);
            d = d.plusDays(7);
        }
        return lst;
    }

    /**
     * Shift to days days after
     *
     * @param days num of days
     * @return DateTimeDuration
     */
    public DateTimeDuration plusDays(long days) {
        DateTimeDuration d = cast(getCopy());
        d.startDateTime = d.startDateTime.plusDays(days);
        d.endDateTime = d.endDateTime.plusDays(days);
        return d;
    }

    /**
     * cast to dateTimeDuration
     *
     * @param another timedDuration
     * @return dateTimeDuration
     */
    private DateTimeDuration cast(TimedDuration another) {
        if (another instanceof DateTimeDuration) {
            return (DateTimeDuration) another;
        } else {
            return ((WeekDayDuration) another).toDateTimeDuration(getStartDate());
        }
    }

    /**
     * Check if another is overlapped with this duration
     *
     * @param another another timedDuration
     * @return isOverlapped?
     */
    public boolean isOverlapping(TimedDuration another) {
        DateTimeDuration d = cast(another);
        return this.startDateTime.compareTo(d.endDateTime) < 0
                && this.endDateTime.compareTo(d.startDateTime) > 0;
    }

    /**
     * Check if another is subset of this
     *
     * @param another another timedDuration
     * @return is subset?
     */
    public boolean isSubsetOf(TimedDuration another) {
        DateTimeDuration d = cast(another);
        return this.startDateTime.compareTo(d.startDateTime) >= 0
                && this.endDateTime.compareTo(d.endDateTime) <= 0;
    }

    /**
     * Check if another is connected with this
     *
     * @param another TimedDuration
     * @return is connected?
     */
    public boolean isConnected(TimedDuration another) {
        DateTimeDuration d = cast(another);
        return this.startDateTime.compareTo(d.endDateTime) <= 0
                && this.endDateTime.compareTo(d.startDateTime) >= 0;
    }

    /**
     * get relative complement of another
     *
     * @param another timed duration
     * @return relative compliment
     */
    public List<TimedDuration> relativeComplementOf(TimedDuration another) {
        DateTimeDuration d = cast(another);
        List<TimedDuration> lst = new ArrayList<>();
        if (isOverlapping(d)) {
            if (d.startDateTime.compareTo(this.startDateTime) > 0) {
                lst.add(DateTimeDuration.parseFromDateTime(this.startDateTime,
                        Duration.between(this.startDateTime, d.startDateTime)));
            }
            if (d.endDateTime.compareTo(this.endDateTime) < 0) {
                lst.add(DateTimeDuration.parseFromDateTime(d.endDateTime,
                        Duration.between(d.endDateTime, this.endDateTime)));
            }
        } else {
            lst.add(this);
        }
        return lst;
    }

    /**
     * intersection with
     *
     * @param another another
     * @return timed duration
     */
    public TimedDuration intersectWith(TimedDuration another) {
        DateTimeDuration d = cast(another);
        LocalDateTime start;
        LocalDateTime end;
        if (d.startDateTime.compareTo(this.startDateTime) > 0) {
            start = d.startDateTime;
        } else {
            start = this.startDateTime;
        }
        if (d.endDateTime.compareTo(this.endDateTime) < 0) {
            end = d.endDateTime;
        } else {
            end = this.endDateTime;
        }
        if (end.compareTo(start) > 0) {
            return new DateTimeDuration(start, end);
        } else {
            return ZERO;
        }
    }

    /**
     * convert this to WeekDauDuration. If duration more than a week, return a whole week
     * @return WeekDayDuration
     */
    public WeekDayDuration toWeekDayDuration() {
        if (this.duration.compareTo(new WeekDayDuration().getDuration()) >= 0) {
            return new WeekDayDuration();
        } else {
            return new WeekDayDuration(getStartDay(), getStartTime(), getEndDay(), getEndTime());
        }
    }

    @Override
    public int compareTo(TimedDuration o) {
        return this.duration.compareTo(o.getDuration());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DateTimeDuration) {
            return this.duration.equals(((DateTimeDuration) obj).duration)
                    && this.startDateTime.equals(((DateTimeDuration) obj).startDateTime);
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public String toString() {
        return this.startDateTime.toString() + " - " + this.endDateTime.toString();
    }

    @Override
    public DateTimeDuration getCopy() {
        return new DateTimeDuration(this.startDateTime, this.endDateTime, this.duration);
    }
}
