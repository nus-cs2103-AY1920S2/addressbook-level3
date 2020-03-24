package seedu.address.model.assignment;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Assignment's Workload in the Schoolwork Tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDuration(String)}
 */
public class Workload {
    public static final String MESSAGE_CONSTRAINTS = "Estimated workload cannot be left empty, "
            + "if no estimates can be made enter '0'. Round off estimates to the nearest hour.";
    public static final String VALIDATION_REGEX = "\\d{1,}";

    // Instance fields
    public final String estHours;

    /**
     * Constructs a {@code Workload}
     *
     * @param estHours Estimated hours required to complete the assignment.
     */
    public Workload(String estHours) {
        requireAllNonNull(estHours);
        checkArgument(isValidDuration(estHours), MESSAGE_CONSTRAINTS);
        this.estHours = estHours;
    }

    /**
     * @param test The input duration to be tested.
     *
     * Returns true if the input duration contains only numbers and contains at least one digit.
     */
    public static boolean isValidDuration(String test) {
        if (test.isEmpty()) {
            return true;
        }
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return estHours + " hours";
    }
}
