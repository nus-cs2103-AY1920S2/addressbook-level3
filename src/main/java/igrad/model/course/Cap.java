package igrad.model.course;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Course Info's cap in the course book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCap(String)}
 */
public class Cap {
    public static final String MESSAGE_CONSTRAINTS = "Cap should not start with a space or slash and should not "
        + "be blank.";

    // The first character of the cap must not be a whitespace, " ", slash; /, or blank.
    public static final String VALIDATION_REGEX = "[0-9](\\.[0-9]+)?";

    public final String value;

    public Cap() {
        value = null;
    }

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Cap(String name) {
        requireNonNull(name);
        checkArgument(isValidCap(name), MESSAGE_CONSTRAINTS);
        value = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCap(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public double getValue() {
        return Double.parseDouble(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Cap // instanceof handles nulls
            && value.equals(((Cap) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
