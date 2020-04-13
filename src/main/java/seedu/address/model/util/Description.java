package seedu.address.model.util;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's description in Mod Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description can take any values, and it should not be more than 64 characters or blank.";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        checkArgument(description == null || isValidDescription(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= 64;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && ((value == null && ((Description) other).value == null)
                || (value != null && ((Description) other).value != null
                && value.equals(((Description) other).value)))); // state check
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

}
