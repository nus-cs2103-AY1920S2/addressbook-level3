package seedu.address.model.assignment;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Assignment's Workload in the Schoolwork Tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidWorkload(String)}
 */
public class Workload {
    public static final String MESSAGE_CONSTRAINTS = "Estimated workload cannot be left empty, "
            + "if no estimates can be made enter '0'. Round off estimates to the nearest half an hour.";
    public static final String VALIDATION_REGEX = "^\\d+\\.?(\\d{0,1}[5])?";

    // Instance fields
    public final String estHours;

    /**
     * Constructs a {@code Workload}
     *
     * @param estHours Estimated hours required to complete the assignment.
     */
    public Workload(String estHours) {
        requireAllNonNull(estHours);
        checkArgument(isValidWorkload(estHours), MESSAGE_CONSTRAINTS);
        this.estHours = estHours;
    }

    /**
     * @param test The input duration to be tested.
     *
     * Returns true if the input duration contains only numbers and contains at least one digit.
     */
    public static boolean isValidWorkload(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return estHours + " hours";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Workload // instanceof handles nulls
                && estHours.equals(((Workload) other).estHours)); // state check
    }
}
