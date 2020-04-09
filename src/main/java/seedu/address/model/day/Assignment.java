package seedu.address.model.day;

/**
 * Represents an assignment that is associated with a given day.
 * Guarantees: Immutable
 */
public class Assignment {
    public final String assignment;

    public Assignment (String assignment) {
        this.assignment = assignment;
    }

    @Override
    public String toString() {
        return assignment;
    }
}
