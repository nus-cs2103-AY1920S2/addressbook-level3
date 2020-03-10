package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's matric number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatric(String)}
 */
public class Matric {

    public static final String MESSAGE_CONSTRAINTS =
            "Matric numbers should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "A" + "\\d{7}" + "[A-Z]";

    public final String value;

    /**
     * Constructs a {@code Matric}.
     *
     * @param matric A valid matric.
     */
    public Matric(String matric) {
        requireNonNull(matric);
        checkArgument(isValidMatric(matric), MESSAGE_CONSTRAINTS);
        value = matric;
    }

    /**
     * Returns true if a given string is a valid matric number.
     */
    public static boolean isValidMatric(String test) {
        return test.matches(VALIDATION_REGEX);
        //return true;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Matric // instanceof handles nulls
                && value.equals(((Matric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
