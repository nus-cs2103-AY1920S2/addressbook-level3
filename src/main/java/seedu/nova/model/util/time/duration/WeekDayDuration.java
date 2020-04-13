package seedu.nova.model.util.time.duration;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import seedu.nova.model.util.time.TimeUtil;

/**
 * Combination of Day of week, time and duration
 */
public class WeekDayDuration implements TimedDuration {
    private LocalDateTime start;
    private LocalDateTime end;
    private Duration duration;

    public WeekDayDuration() {
        this.start = TimeUtil.toDateTime(DayOfWeek.MONDAY, TimeUtil.EX_DATE, TimeUtil.BEGIN_DAY_TIME);
        this.end = TimeUtil.toDateTime(DayOfWeek.SUNDAY, TimeUtil.EX_DATE, TimeUtil.END_DAY_TIME);
        this.duration = Duration.between(this.start, this.end);
    }

    public WeekDayDuration(DayOfWeek startDow, LocalTime startTime, LocalTime endTime) {
        this.start = TimeUtil.toDateTime(startDow, TimeUtil.EX_DATE, startTime);
        this.end = TimeUtil.toDateTime(startDow, TimeUtil.EX_DATE, endTime);
        if (this.start.compareTo(this.end) > 0) {
            this.end = this.end.plusDays(1);
        }
        this.duration = Duration.between(this.start, this.end);
    }

    public WeekDayDuration(DayOfWeek startDow, LocalTime startTime, DayOfWeek endDow, LocalTime endTime) {
        this.start = TimeUtil.toDateTime(startDow, TimeUtil.EX_DATE, startTime);
        this.end = TimeUtil.toDateTime(endDow, TimeUtil.EX_DATE, endTime);
        if (this.start.compareTo(this.end) > 0) {
            this.end = this.end.plusDays(7);
        }
        this.duration = Duration.between(this.start, this.end);
    }

    private WeekDayDuration(LocalDateTime start, LocalDateTime end, Duration duration) {
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

    /**
     * Make a WeekDayDuration representation of duration for comparison purpose only.
     * @param duration = duration
     * @return WeekDayDurationc
     */
    public static WeekDayDuration parseDuration(Duration duration) {
        return new WeekDayDuration(null, null, duration);
    }

    public static WeekDayDuration parseDay(DayOfWeek dow) {
        return new WeekDayDuration(dow, TimeUtil.BEGIN_DAY_TIME, TimeUtil.END_DAY_TIME);
    }

    public boolean isZero() {
        return this.duration.isZero();
    }

    public int getStartValue() {
        return TimedDuration.getDayTimeValue(getStartDay(), getStartTime());
    }

    public int getEndValue() {
        return TimedDuration.getDayTimeValue(getEndDay(), getEndTime());
    }

    /**
     * get Week of the date, in DateTimeDuration
     *
     * @param sameWeek date
     * @return DateTimeDuration
     */
    public DateTimeDuration toDateTimeDuration(LocalDate sameWeek) {
        LocalDateTime startDate = LocalDateTime.of(TimeUtil.dateOfSameWeek(getStartDay(), sameWeek), getStartTime());
        LocalDateTime endDate = LocalDateTime.of(TimeUtil.dateOfSameWeek(getEndDay(), sameWeek), getEndTime());
        if (getStartValue() > getEndValue()) {
            endDate = endDate.plusDays(7);
        }
        return new DateTimeDuration(startDate, endDate);
    }


    public DayOfWeek getStartDay() {
        return this.start.getDayOfWeek();
    }


    public LocalTime getStartTime() {
        return this.start.toLocalTime();
    }


    public DayOfWeek getEndDay() {
        return this.end.getDayOfWeek();
    }


    public LocalTime getEndTime() {
        return this.end.toLocalTime();
    }


    public Duration getDuration() {
        return this.duration;
    }

    private DateTimeDuration getDtd() {
        return new DateTimeDuration(this.start, this.end);
    }

    @Override
    public boolean isConnected(TimedDuration another) {
        if (another instanceof DateTimeDuration) {
            return another.isConnected(toDateTimeDuration(((DateTimeDuration) another).getStartDate()));
        } else {
            return getDtd().isConnected(((WeekDayDuration) another).getDtd());
        }
    }

    @Override
    public boolean isOverlapping(TimedDuration another) {
        if (another instanceof DateTimeDuration) {
            return another.isOverlapping(toDateTimeDuration(((DateTimeDuration) another).getStartDate()));
        } else {
            return getDtd().isOverlapping(((WeekDayDuration) another).getDtd());
        }
    }

    @Override
    public boolean isSubsetOf(TimedDuration another) {
        if (another instanceof DateTimeDuration) {
            return toDateTimeDuration(((DateTimeDuration) another).getStartDate()).isSubsetOf(another);
        } else {
            return getDtd().isSubsetOf(((WeekDayDuration) another).getDtd());
        }
    }

    @Override
    public List<TimedDuration> relativeComplementOf(TimedDuration another) {
        if (another instanceof WeekDayDuration) {
            return getDtd().relativeComplementOf(((WeekDayDuration) another).getDtd())
                    .parallelStream()
                    .map(x -> ((DateTimeDuration) x).toWeekDayDuration())
                    .collect(Collectors.toList());
        } else {
            return getDtd().relativeComplementOf(another)
                    .parallelStream()
                    .map(x -> ((DateTimeDuration) x).toWeekDayDuration())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public TimedDuration intersectWith(TimedDuration another) {
        if (another instanceof DateTimeDuration) {
            return (
                    (DateTimeDuration) another
                            .intersectWith(toDateTimeDuration(((DateTimeDuration) another)
                                    .getStartDate()))).toWeekDayDuration();
        } else {
            return ((DateTimeDuration) getDtd()
                    .intersectWith(((WeekDayDuration) another)
                            .getDtd())).toWeekDayDuration();
        }
    }


    public int compareTo(TimedDuration another) {
        return getDuration().compareTo(another.getDuration());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WeekDayDuration) {
            return getStartValue() == (((WeekDayDuration) obj).getStartValue())
                    && getEndValue() == (((WeekDayDuration) obj).getEndValue());
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public String toString() {
        return getStartDay().toString()
                + "T" + getStartTime() + "\n" + getEndDay().toString() + "T" + getEndTime() + "\n";
    }


    public WeekDayDuration getCopy() {
        return new WeekDayDuration(this.start, this.end, this.duration);
    }
}
