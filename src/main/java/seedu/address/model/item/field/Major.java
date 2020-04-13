package seedu.address.model.item.field;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the major field of a user profile in the resume book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMajor(String)}
 */
public class Major {

    public static final String MESSAGE_CONSTRAINTS = "Major should only contain alphanumeric characters and spaces, "
        + "with max length of 50 characters and it should not be blank";

    /*
     * The first character of the major must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Major}.
     *
     * @param major A valid major.
     */
    public Major(String major) {
        requireNonNull(major);
        checkArgument(isValidMajor(major), MESSAGE_CONSTRAINTS);
        value = major;
    }

    /**
     * Returns true if a given string is a valid major.
     */
    public static boolean isValidMajor(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Major // instanceof handles nulls
                && value.equals(((Major) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
