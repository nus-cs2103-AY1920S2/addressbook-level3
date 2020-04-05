package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's attendance in the address book.
 */
public class Attendance {


    public static final String MESSAGE_CONSTRAINTS =
            "Attendance should either be \"Present\", \"Absent\", \"Late\" or \"Sick\".";
    public static final String VALIDATION_REGEX_PRESENT = "Present";
    public static final String VALIDATION_REGEX_ABSENT = "Absent";
    public static final String VALIDATION_REGEX_LATE = "Late";
    public static final String VALIDATION_REGEX_SICK = "Sick";
    public final String value;

    /**
     * Constructs a {@code Attendance}.
     *
     * @param state A valid attendance state.
     */
    public Attendance(String state) {
        requireNonNull(state);
        checkArgument(isValidAttendance(state), MESSAGE_CONSTRAINTS);
        value = state;
    }

    /**
     * Returns true if a given string is a valid attendance.
     */
    public static boolean isValidAttendance(String test) {
        return test.matches(VALIDATION_REGEX_ABSENT)
                || test.matches(VALIDATION_REGEX_PRESENT) || test.matches(VALIDATION_REGEX_LATE)
                || test.matches(VALIDATION_REGEX_SICK);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendance // instanceof handles nulls
                && value.equals(((Attendance) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.toString().hashCode();
    }
}
