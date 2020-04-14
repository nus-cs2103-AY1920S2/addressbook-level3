package fithelper.model.entry;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Entry's time in FitHelper.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Time should be in format: yyyy-MM-dd-HH:mm";

    public static final DateTimeFormatter PARSEFORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    public final LocalDate date;
    public final LocalTime time;
    public final LocalDateTime dateTime;
    public final String value;

    /**
     * Constructs an {@code Time}.
     * @param timeStr A valid time.
     */
    public Time(String timeStr) {
        requireNonNull(timeStr);
        checkArgument(isValidTime(timeStr), MESSAGE_CONSTRAINTS);
        time = LocalTime.parse(timeStr, PARSEFORMAT);
        date = LocalDate.parse(timeStr, PARSEFORMAT);
        dateTime = LocalDateTime.parse(timeStr, PARSEFORMAT);
        value = concat(time, date);
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        try {
            LocalTime.parse(test, PARSEFORMAT);
            LocalDate.parse(test, PARSEFORMAT);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String concat(LocalTime time, LocalDate date) {
        return date + "-" + time;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


    public LocalDate getDate() {
        return this.date;
    }

    public String getDateStr() {
        return this.date.toString();
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

}
