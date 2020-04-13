package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's matric number in TAble.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricNumber(String)}
 */
public class MatricNumber {


    public static final String MESSAGE_CONSTRAINTS =
            "Matric number should be of the format A[Number][Letter], for example A1234567Y."
                    + " And adhere to the following constraints:\n"
                    + "1. Student ID must only be 9 characters long\n"
                    + "2. The numerical part must be 7 characters long and contain only numbers\n"
                    + "1. Both A and Letter must be in CAPS.";

    private static final String INITIAL_REGEX = "[A]";
    private static final String NUMBER_REGEX = "\\d{7}";
    private static final String LETTER_REGEX = "[A-Z]";
    public static final String VALIDATION_REGEX = INITIAL_REGEX + NUMBER_REGEX + LETTER_REGEX;
    public final String value;

    /**
     * Constructs a {@code MatricNumber}.
     *
     * @param matricNumber A valid matric number.
     */
    public MatricNumber(String matricNumber) {
        requireNonNull(matricNumber);
        checkArgument(isValidMatricNumber(matricNumber), MESSAGE_CONSTRAINTS);
        value = matricNumber;
    }

    /**
     * Returns true if a given string is a valid matric number.
     */
    public static boolean isValidMatricNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNumber // instanceof handles nulls
                && value.equals(((MatricNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
