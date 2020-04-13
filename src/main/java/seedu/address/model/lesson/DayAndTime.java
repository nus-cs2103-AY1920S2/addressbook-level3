package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import seedu.address.model.lesson.exceptions.InvalidDayAndTimeException;
import seedu.address.model.lesson.exceptions.InvalidTimeRangeException;

/**
 * Represents day and time in Lesson.
 */
public class DayAndTime implements Comparable<DayAndTime> {

    public static final String MESSAGE_CONSTRAINTS_DAY_AND_TIME =
            "Day should only be either MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY or SUNDAY\n"
                    + "and time should be in HH:MM 24 hours format. Day, start time and end time must be all present.";

    public static final String MESSAGE_CONSTRAINTS_DAY =
            "Day should only be either MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY or SUNDAY";

    public static final String MESSAGE_CONSTRAINTS_TIME =
            "Time should be in HH:MM 24 hours format and start time should be earlier than end time";

    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;

    public DayAndTime(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        requireAllNonNull(day, startTime, endTime);
        if (startTime.compareTo(endTime) >= 0) {
            throw new InvalidTimeRangeException();
        }
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     *
     * @param dayAndTime
     */
    public DayAndTime(String dayAndTime) {
        requireNonNull(dayAndTime);
        if (!isValidDayAndTime(dayAndTime)) {
            throw new InvalidDayAndTimeException();
        }
        String[] splitted = dayAndTime.split("\\s");
        DayOfWeek day = DayOfWeek.valueOf(splitted[0].toUpperCase());
        LocalTime startTime = LocalTime.parse(splitted[1]);
        LocalTime endTime = LocalTime.parse(splitted[2]);
        if (startTime.compareTo(endTime) >= 0) {
            throw new InvalidTimeRangeException();
        }
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Compares the instance of dayOfWeek to {@code dayOfWeek}.
     */
    public int compareTo(DayAndTime dayAndTime) {
        DayOfWeek day = dayAndTime.getDay();
        LocalTime time = dayAndTime.getStartTime();
        int val = this.getDay().compareTo(day);
        if (val > 0) {
            return 1;
        } else if (val < 0) {
            return -1;
        } else {
            return this.getStartTime().compareTo(time);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DayAndTime)) {
            return false;
        }

        DayAndTime otherDayAndTime = (DayAndTime) other;
        return otherDayAndTime.getDay().equals(getDay())
                && otherDayAndTime.getStartTime().equals(getStartTime())
                && otherDayAndTime.getEndTime().equals(getEndTime());
    }

    /**
     * Checks if time is a valid time.
     */
    public static boolean isValidTimeFormat(String time) {
        try {
            LocalTime.parse(time);
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if dayString is a valid day.
     */
    public static boolean isValidDay(String dayString) {
        String day = dayString.toUpperCase();
        for (int i = 0; i < 7; i++) {
            if (DayOfWeek.values()[i].toString().equals(day)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if dayAndTimeString is valid.
     */
    public static boolean isValidDayAndTime(String dayAndTimeString) {
        String[] splitted = dayAndTimeString.split(" ");
        if (splitted.length != 3) {
            return false;
        }
        String dayString = splitted[0].toUpperCase();
        String startTimeString = splitted[1];
        String endTimeString = splitted[2];

        return isValidDay(dayString) && isValidTimeFormat(startTimeString) && isValidTimeFormat(endTimeString);
    }

    @Override
    public String toString() {
        return getDay() + " " + getStartTime() + "-" + getEndTime();
    }

    /**
     * Checks if both time slot clashes.
     */
    public boolean isSameTimeSlot(DayAndTime otherDayAndTime) {
        LocalTime thisStartTime = getStartTime();
        LocalTime thisEndTime = getEndTime();
        LocalTime otherStartTime = otherDayAndTime.getStartTime();
        LocalTime otherEndTime = otherDayAndTime.getEndTime();
        return (getDay().equals(otherDayAndTime.getDay()))
                && (thisStartTime.compareTo(otherEndTime) < 0 && otherStartTime.compareTo(thisEndTime) < 0);
    }
}
