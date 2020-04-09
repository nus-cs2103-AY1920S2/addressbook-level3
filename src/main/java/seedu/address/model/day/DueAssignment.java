package seedu.address.model.day;

/**
 * Represents an assignment that is due on the given day.
 * Guarantees: Immutable
 */
public class DueAssignment {
    public final String assignment;

    public DueAssignment (String assignment) {
        this.assignment = assignment;
    }
}
