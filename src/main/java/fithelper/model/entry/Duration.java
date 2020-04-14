package fithelper.model.entry;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Entry's duration in FitHelper.
 * Guarantees: immutable; is valid as declared in {@link #isValidDuration(String)}
 */
public class Duration {

    public static final String MESSAGE_CONSTRAINTS = "Duration can only be greater than or equals to 0.02 (hrs),"
            + "\n with smallest accuracy set to 0.02 (1 min)";

    /*
     * The first character of the entry name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param duration A valid duration.
     */
    public Duration(String duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS);
        value = duration;
    }

    /**
     * Returns true if a given string is a valid duration.
     */
    public static boolean isValidDuration(String test) {
        boolean isValid;
        try {
            double temp = Double.parseDouble(test);
            if (temp >= 0.02) {
                if (test.matches(VALIDATION_REGEX)) {
                    isValid = true;
                } else {
                    isValid = false;
                }
            } else {
                isValid = false;
            }
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public String toString() {
        return value;
    }

    public long getHours() {
        long hours = 0;
        hours = (long) Double.parseDouble(value);
        return hours;
    }

    public long getMinutes() {
        int indexOfDecimal = value.indexOf(".");
        if (indexOfDecimal < 0) {
            return 0;
        }
        Double minutes = Double.parseDouble(value.substring((indexOfDecimal)));
        return (long) (60 * minutes);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && value.equals(((Name) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
