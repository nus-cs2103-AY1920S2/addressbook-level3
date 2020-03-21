package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's time number in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {


    public static final String MESSAGE_CONSTRAINTS =
            "Time should only contain whole numbers in terms of minutes, and it should be at least 1 digit long";
    public static final String TIME_RANGE_CONSTRANTS = "Time or time range should only contain whole numbers in terms "
            + "of minutes, be at least 1 digit long, and be separated by a single dash.\n"
            + "Example: filter t/10 or filter t/10-20";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time number.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.value = time;
    }

    /**
     * Returns true if a given string is a valid time number.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the value of the given time object is less than or equal to the value of this time object.
     */
    public boolean isLessThan(Time time) {
        return Integer.parseInt(time.value) <= Integer.parseInt(value);
    }

    /**
     * Returns true if the value of this time object is within the range of the values of the given lower and upper
     * bound time objects.
     */
    public boolean isWithinRange(Time lowerBound, Time upperBound) {
        int valueAsInt = Integer.parseInt(value);
        return valueAsInt >= Integer.parseInt(lowerBound.value)
                && valueAsInt <= Integer.parseInt(upperBound.value);
    }

    @Override
    public String toString() {
        return value;
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
