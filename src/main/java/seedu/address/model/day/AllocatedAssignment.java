package seedu.address.model.day;

/**
 * Represents an assignment whose workload is allocated to a Day.
 */
public class AllocatedAssignment extends Assignment {
    public final float hours;

    public AllocatedAssignment(float hours, String title) {
        super(title);
        this.hours = hours;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + hours + ")";
    }
}
