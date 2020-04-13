package seedu.address.model.task.util;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;



/**
 * Represents a Module's taskTime in Mod Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskTime(String)}
 */
public class TaskDateTime implements Comparable {
    public static final String MESSAGE_CONSTRAINTS =
            "Date and time must conform to the format dd/mm/yyyy and hh:mm.\n"
            + "These data must also be valid!";

    private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendPattern("dd/MM/yyyy[ HH:mm]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

    public static final LocalDateTime SPECIAL_DATE_TIME =
            LocalDate.parse("01/01/1970", dateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();

    private static Logger logger = LogsCenter.getLogger(TaskDateTime.class);

    private LocalDateTime taskTime;

    /**
     * Constructs a {@code LocalDateTime}.
     *
     * @param date A valid date.
     */
    public TaskDateTime(String date) {
        checkArgument(isValidTaskTime(date), MESSAGE_CONSTRAINTS);
        try {
            this.taskTime = LocalDate.parse(date, dateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
        } catch (DateTimeParseException dateError) {
            logger.warning(
                    String.format(
                            "%s error: Please include code to check for valid date time first", date));
        }
    }

    /**
     * Constructs a {@code LocalDateTime}.
     *
     * @param date A valid date.
     * @param timeInDay time in day
     */
    public TaskDateTime(String date, String timeInDay) {
        checkArgument(isValidTaskTime(date + " " + timeInDay), MESSAGE_CONSTRAINTS);
        try {
            LocalTime timeInTheDay = LocalTime.parse(timeInDay, dateTimeFormatter.ofPattern("HH:mm"));
            this.taskTime = LocalDateTime.of(LocalDate.parse(date, dateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    timeInTheDay);
        } catch (DateTimeParseException timeError) {
            logger.warning(
                    String.format(
                            "%s %s error: Please include code to check for valid date time first", date, timeInDay));
        }
    }

    public TaskDateTime(LocalDateTime localDateTime) {
        this.taskTime = localDateTime;
    }

    /**
     * Returns true if a given string is a valid taskTime.
     */
    public static boolean isValidTaskTime(String test) {
        // to have further check on this!
        logger.info(test);
        int testLen = test.length();
        if (testLen != 10 && testLen != 16) {
            return false;
        } else if (testLen == 10) {
            try {
                LocalDate.parse(test, dateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
            } catch (DateTimeParseException dateStringError) {
                logger.info("Failed date " + test);
                return false;
            }
            return true;
        } else {
            String[] splitted = test.split("\\s");
            if (splitted.length != 2) {
                return false;
            }
            try {
                LocalTime.parse(splitted[1], dateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException timeError) {
                logger.info("Failed time " + test);
                return false;
            }
            try {
                LocalDate.parse(splitted[0], dateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException dateError) {
                logger.info("Failed date " + test);
                return false;
            }
            return true;
        }
    }

    public LocalTime toLocalTime() {
        return taskTime.toLocalTime();
    }

    /**
     * outputs the Date in the week of the task
     */
    protected String getDateInWeek() {
        if (taskTime == null) {
            return "";
        }
        DayOfWeek dayInWeek = taskTime.getDayOfWeek();
        String title = dayInWeek.toString();
        return title.charAt(0) + title.substring(1).toLowerCase();
    }

    public LocalDateTime getLocalDateTime() {
        return taskTime;
    }

    public boolean isDateOnThisDay(int day) {
        return taskTime.getDayOfMonth() == day;
    }

    public boolean isDateOnThisMonth(int month) {
        return taskTime.getMonthValue() == month;
    }

    public boolean isDateOnThisYear(int year) {
        return taskTime.getYear() == year;
    }

    /**
     * Compares the two Deadlines for order.
     * Nearer deadlines are considered smaller
     */
    @Override
    public int compareTo(Object other) {
        assert (other instanceof TaskDateTime); // can only compare between Tasks
        TaskDateTime otherTask = (TaskDateTime) other;
        if (taskTime.isAfter(otherTask.taskTime)) {
            return 1;
        } else if (taskTime.isBefore(otherTask.taskTime)) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        if (taskTime == null) {
            return "";
        }
        // note for Command
        // prevent users from creating 00:00 time. For this time, the user should create an all day Task instead.
        if (taskTime.isEqual(taskTime.toLocalDate().atStartOfDay())) { // no time in day
            return taskTime.format(dateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return taskTime.format(dateTimeFormatter.ofPattern("dd/MM/yyyy[ HH:mm]"));
    }

    @Override
    public int hashCode() {
        return taskTime != null ? taskTime.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof TaskDateTime)) {
            return false;
        }

        return this.taskTime.equals(((TaskDateTime) obj).taskTime);
    }
}
