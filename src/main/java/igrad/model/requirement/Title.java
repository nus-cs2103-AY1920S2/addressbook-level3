package igrad.model.requirement;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Requirement's title.
 * Guarantees: immutable, non-null and is valid as declared by {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS = "Title should not start with a space or slash,"
                                                         + " should not contain only numbers,"
                                                         + " and should not be blank.";

    // The first character of the requirement title must not be a whitespace (" ").
    // The title must not contain only numbers or be blank.
    public static final String VALIDATION_REGEX = "^[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Title}.
     *
     * @param value A valid title string.
     */
    public Title(String value) {
        requireNonNull(value);
        checkArgument(isValidTitle(value), MESSAGE_CONSTRAINTS);

        this.value = value;
    }

    /**
     * Returns true if the given string {@code test} is a valid title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object, else check
            || (other instanceof Title && value.equals(((Title) other).value)); // check same type and value
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
