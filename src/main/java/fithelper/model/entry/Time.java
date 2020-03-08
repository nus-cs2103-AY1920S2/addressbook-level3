package fithelper.model.entry;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Entry's time in FitHelper.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Time should be in format: yyyy-MM-dd-HH:mm";

    public static final DateTimeFormatter PARSEFORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    public final LocalTime value;

    /**
     * Constructs an {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        value = LocalTime.parse(time, PARSEFORMAT);
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        try {
            LocalTime.parse(test, PARSEFORMAT);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value.toString();
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

}
