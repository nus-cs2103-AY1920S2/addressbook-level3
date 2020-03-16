package seedu.address.model.assignment;

/**
 * Represents an Assignment's Status in the Schoolwork Tracker.
 * Guarantees: immutable;
 */
public class Status {
    public final String status;

    public Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[" + status + "]";
    }
}
