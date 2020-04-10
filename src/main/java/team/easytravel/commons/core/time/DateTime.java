package team.easytravel.commons.core.time;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Represents a DateTime object in ET which wraps around the java.time.LocalDateTime and provides basic
 * functionality such as comparision between time.
 */
public class DateTime {
    public static final String MESSAGE_CONSTRAINTS = "Date-Time entered must be in the format of <dd-MM-yyyy HH:mm>.";

    private static final DateTimeFormatter FORMAT_INPUT = DateTimeFormatter.ofPattern("d-M-yyyy H:m");
    private static final DateTimeFormatter FORMAT_STORAGE = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final DateTimeFormatter FORMAT_OUTPUT = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mma");

    private final LocalDateTime dateTime;

    /**
     * DateTime can only be created by calling {@link #fromString(String)}.
     */
    private DateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Compare this date-time with another date-time.
     * <p>
     * The comparison is primarily based on the date-time, from earliest to latest. It is consistent with equals(),
     * as defined by Comparable.
     */
    public int compareTo(DateTime other) {
        return dateTime.compareTo(other.dateTime);
    }

    /**
     * Returns this DateTime in the user input format.
     */
    public String getStorageFormat() {
        return dateTime.format(FORMAT_STORAGE);
    }

    public Time getTime() {
        return new Time(dateTime.toLocalTime());
    }

    public Date getDate() {
        return new Date(dateTime.toLocalDate());
    }

    /**
     * Returns a copy of this DateTime with the specified number of hours added.
     */
    public DateTime plusHours(long hours) throws IllegalArgumentException {
        return new DateTime(dateTime.plusHours(hours));
    }

    /**
     * Returns the number of hours from this time till the specified end {@code Time} (inclusive).
     */
    public long hoursUntilExclusive(DateTime endExclusive) {
        return dateTime.until(endExclusive.dateTime, ChronoUnit.HOURS);
    }

    /**
     * Creates a new {@code DateTime} from the given properly formatted {@code dateTimeString}.
     */
    public static DateTime fromString(String dateTimeString) {
        requireNonNull(dateTimeString);
        checkArgument(isValidDateTime(dateTimeString), MESSAGE_CONSTRAINTS);
        return new DateTime(LocalDateTime.parse(dateTimeString, FORMAT_INPUT));
    }

    /**
     * Creates an instance of DateTime from the given Date and Time.
     */
    public static DateTime of(Date date, Time time) {
        return new DateTime(LocalDateTime.of(date.date, time.time));
    }

    /**
     * Returns true if a given string is a valid DateTime format.
     */
    public static boolean isValidDateTime(String test) {
        try {
            LocalDateTime.parse(test, FORMAT_INPUT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && dateTime.equals(((DateTime) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

    @Override
    public String toString() {
        return dateTime.format(FORMAT_OUTPUT);
    }
}
