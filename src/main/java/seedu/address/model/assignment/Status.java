package seedu.address.model.assignment;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Assignment's Status in the Schoolwork Tracker.
 * Guarantees: immutable;
 */
public class Status {
    public static final String ASSIGNMENT_DONE = "Completed";
    public static final String ASSIGNMENT_OUTSTANDING = "Uncompleted";
    public static final String MESSAGE_CONSTRAINTS = "Status can only be " + ASSIGNMENT_DONE + " or "
        + ASSIGNMENT_OUTSTANDING;

    // Instance variable
    public final String status;

    public Status(String status) {
        requireAllNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        this.status = status;
    }

    public static boolean isValidStatus(String status) {
        return status.equals(ASSIGNMENT_OUTSTANDING) || status.equals(ASSIGNMENT_DONE);
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && status.equals(((Status) other).status)); // state check
    }
}
