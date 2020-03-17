package seedu.zerotoone.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.AppUtil.checkArgument;

/**
 * Represents a Set's interval in ZeroToOne.
 * Guarantees: immutable; is valid as declared in {@link #isValidInterval(String)}
 */
public class Interval {

    public static final String MESSAGE_CONSTRAINTS = "Interval should only be one number.";

    /*
     * The first character of the interval must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\d{1,}";

    public final String value;

    /**
     * Constructs an {@code Interval}.
     *
     * @param interval A valid interval.
     */
    public Interval(String interval) {
        requireNonNull(interval);
        checkArgument(isValidInterval(interval), MESSAGE_CONSTRAINTS);
        value = interval;
    }

    /**
     * Returns true if a given string is a valid interval.
     */
    public static boolean isValidInterval(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Interval // instanceof handles nulls
                && value.equals(((Interval) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
