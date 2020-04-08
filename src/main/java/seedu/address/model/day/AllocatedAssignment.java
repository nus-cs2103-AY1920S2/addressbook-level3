package seedu.address.model.day;

/**
 * Represents an assignment whose workload is allocated to a Day.
 */
public class AllocatedAssignment extends Assignment {
    public final float allocatedHours;

    public AllocatedAssignment(float allocatedHours, String assignment) {
        super(assignment);
        this.allocatedHours = allocatedHours;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + allocatedHours + ")";
    }
}
