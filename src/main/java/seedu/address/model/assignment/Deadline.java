package seedu.address.model.assignment;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Represents an Assignment's Deadline in the Schoolwork Tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)} and {@link #isValidTime(String)}
 */
public class Deadline {
    public static final String MESSAGE_CONSTRAINTS =
            "Deadline cannot be empty and has to be in MM-dd HH:mm (24-hour clock) format";
    private static final DateTimeFormatter inputDateFormat = new DateTimeFormatterBuilder().appendPattern("MM-dd")
            .parseDefaulting(ChronoField.YEAR, 2020).toFormatter();
    private static final DateTimeFormatter inputTimeFormat = new DateTimeFormatterBuilder()
            .appendPattern("HH:mm").toFormatter();
    private static final DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("dd MMM");
    private static final DateTimeFormatter outputTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");

    // Instance variables
    public final String date;
    public final String time;
    public final LocalDateTime dateTime;

    /**
     * Constructs a {@code Deadline}
     *
     * @param date The due date of the assignment.
     * @param time The time by which the assignment needs to be submitted on the due date.
     */
    public Deadline(String date, String time) {
        requireAllNonNull(date, time);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);

        LocalDate parsedDate = LocalDate.parse(date, inputDateFormat);
        LocalTime parsedTime = LocalTime.parse(time, inputTimeFormat);
        LocalDateTime dt = LocalDateTime.of(parsedDate, parsedTime);

        this.date = date;
        this.time = time;
        dateTime = dt;
    }

    /**
     * Getter for datetime object.
     * @return datetime of deadline
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * @param test The input date to be tested.
     *
     * Returns true if date is not an empty string and follows the required format.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, inputDateFormat);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * @param test The input time to be tested.
     *
     * Returns true if time is not an empty string and requires the required format.
     */
    public static boolean isValidTime(String test) {
        try {
            LocalTime.parse(test, inputTimeFormat);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        LocalDate parsedDate = LocalDate.parse(date, inputDateFormat);
        LocalTime parsedTime = LocalTime.parse(time, inputTimeFormat);
        return parsedDate.format(outputDateFormat) + " " + parsedTime.format(outputTimeFormat);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && date.equals(((Deadline) other).date) && time.equals(((Deadline) other).time)); // state check
    }
}
