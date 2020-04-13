//@@author fatin99

package tatracker.model.student;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's matric number in the TA-Tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatric(String)}
 */
public class Matric implements Comparable<Matric> {

    public static final String MESSAGE_CONSTRAINTS =
            "Matric numbers should start with an \"A\", followed by 7 digits, and one final capital letter,"
                    + " and it should not be blank";

    /*
     * matric number must start with an "A" followed by 7 digits and one final capital letter
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

    @Override
    public int compareTo(Matric other) {
        return value.compareToIgnoreCase(other.value);
    }
}
