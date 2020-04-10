package team.easytravel.commons.core.time;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Represents a Time object in ET which wraps around the java.time.LocalTime and provides basic
 * functionality such as comparision between Time.
 */
public class Time {
    public static final String MESSAGE_CONSTRAINTS = "Time entered must be in the format of <HH:mm>.";

    private static final DateTimeFormatter FORMAT_INPUT = DateTimeFormatter.ofPattern("H:m");
    private static final DateTimeFormatter FORMAT_STORAGE = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FORMAT_OUTPUT = DateTimeFormatter.ofPattern("hh:mma");

    private static final String MESSAGE_ERROR_ADD = "%1$d hours cannot be added to %2$s";
    private static final String MESSAGE_ERROR_UNTIL = "End time must not be earlier than current time.";

    protected final LocalTime time;

    /**
     * Time can only be created by calling {@link #fromString(String)}.
     */
    protected Time(LocalTime time) {
        this.time = time;
    }

    /**
     * Compare this time with another time.
     * <p>
     * The comparison is primarily based on the time, from earliest to latest. It is consistent with equals(),
     * as defined by Comparable.
     */
    public int compareTo(Time other) {
        return time.compareTo(other.time);
    }

    /**
     * Returns this time in the user input format.
     */
    public String getStorageFormat() {
        return time.format(FORMAT_STORAGE);
    }

    /**
     * Returns a copy of this Time with the specified number of hours added.
     *
     * @throws IllegalArgumentException When resulting time is over 00:00hrs.
     */
    public Time plusHours(long hours) throws IllegalArgumentException {
        LocalTime result = time.plusHours(hours);
        if (result.getHour() == 0) {
            throw new IllegalArgumentException(String.format(MESSAGE_ERROR_ADD, hours, this));
        }
        return new Time(result);
    }

    /**
     * Returns the number of hours from this time till the specified end {@code Time} (inclusive).
     */
    public long hoursUntilExclusive(Time endExclusive) {
        // If end time is 00:00hr, return 24 - currentTimeHour
        if (endExclusive.time.getHour() == 0) {
            return 24 - time.getHour();
        }

        if (time.compareTo(endExclusive.time) > 0) {
            throw new AssertionError(MESSAGE_ERROR_UNTIL);
        }

        return time.until(endExclusive.time, ChronoUnit.HOURS);
    }

    /**
     * Creates a new {@code Time} from the given properly formatted {@code timeString}.
     */
    public static Time fromString(String timeString) {
        requireNonNull(timeString);
        checkArgument(isValidTime(timeString), MESSAGE_CONSTRAINTS);
        return new Time(LocalTime.parse(timeString, FORMAT_INPUT));
    }

    /**
     * Returns true if a given string is a valid Time format.
     */
    public static boolean isValidTime(String test) {
        try {
            LocalTime.parse(test, FORMAT_INPUT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.equals(((Time) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    @Override
    public String toString() {
        return time.format(FORMAT_OUTPUT);
    }
}
