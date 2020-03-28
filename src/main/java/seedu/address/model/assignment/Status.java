package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Assignment's Status in the Schoolwork Tracker.
 * Guarantees: immutable;
 */
public class Status {
    public static final String ASSIGNMENT_DONE = "Completed";
    public static final String ASSIGNMENT_OUTSTANDING = "Not completed";

    // Instance variable
    public final String status;

    public Status(String status) {
        requireAllNonNull(status);
        this.status = status;
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
