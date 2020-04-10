package seedu.address.model.item.field;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the university field of a user profile in the resume book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUniversity(String)}
 */
public class University {

    // TODO: Utilise this class in main codes

    public static final String MESSAGE_CONSTRAINTS = "University field can take any value "
            + " and it should not be blank.";

    /*
     * The first character of the university must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code University}.
     *
     * @param university A valid university.
     */
    public University(String university) {
        requireNonNull(university);
        checkArgument(isValidUniversity(university), MESSAGE_CONSTRAINTS);
        value = university;
    }

    /**
     * Returns true if a given string is a valid university.
     */
    public static boolean isValidUniversity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof University // instanceof handles nulls
                && value.equals(((University) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
