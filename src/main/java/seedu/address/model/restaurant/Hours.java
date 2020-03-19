package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Restaurant's hours in the restaurant book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHours(String)}
 */
public class Hours {

    public static final String MESSAGE_CONSTRAINTS =
            "Hours should only contain of this format XXXX:YYYY, where XXXX and YYYY are the opening & closing times.";

    /*
     *
     */
    public static final String VALIDATION_REGEX =
            "(([0-1][0-9][0-5][0-9]|[2][0-3][0-5][0-9]):([0-1][0-9][0-5][0-9]|[2][0-3][0-5][0-9]))|^$";

    public final String hours;

    /**
     * Constructs a {@code Hours}.
     *
     * @param hours A valid hours.
     */
    public Hours(String hours) {
        requireNonNull(hours);
        checkArgument(isValidHours(hours), MESSAGE_CONSTRAINTS);
        this.hours = hours;
    }

    /**
     * Returns true if a given string is a valid hours.
     */
    public static boolean isValidHours(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return hours;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Hours // instanceof handles nulls
                && hours.equals(((Hours) other).hours)); // state check
    }

    @Override
    public int hashCode() {
        return hours.hashCode();
    }

}
