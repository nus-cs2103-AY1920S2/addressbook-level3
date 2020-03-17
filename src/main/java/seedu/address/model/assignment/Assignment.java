package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an assignment that a user can keep track of.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Assignment {
    // Identity Fields
    private final Title title;

    // Data Fields
    private final Deadline deadline;
    private final Workload estHours;
    private final Status status;

    /**
     * Every field must be present and not null.
     *
     * @param title The description of the assignment to be completed.
     * @param deadline The deadline by when the assignment needs to be completed.
     * @param estHours The estimated hours required to complete the assignment.
     */
    public Assignment(Title title, Deadline deadline, Workload estHours) {
        requireAllNonNull(title, deadline, estHours);
        this.title = title;
        this.deadline = deadline;
        this.estHours = estHours;
        this.status = new Status("Not completed");
    }

    /**
     * Constructor to be used when loading saved assignments.
     * Every field must be present and not null.
     *
     * @param title The description of the assignment to be completed.
     * @param deadline The deadline by when the assignment needs to be completed.
     * @param estHours The estimated hours required to complete the assignment.
     * @param status The status of the assignment.
     */
    public Assignment(Title title, Deadline deadline, Workload estHours, Status status) {
        requireAllNonNull(title, deadline, estHours);
        this.title = title;
        this.deadline = deadline;
        this.estHours = estHours;
        this.status = status;
    }



    public Title getTitle() {
        return title;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Workload getWorkload() {
        return estHours;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Assignment: ")
                .append(getTitle())
                .append("\nDue Date: ")
                .append(getDeadline())
                .append("\nEstimated work hours: ")
                .append(getWorkload())
                .append("\nStatus: ")
                .append(getStatus())
                .append("\n");
        return builder.toString();
    }

    /**
     * Returns true if both assignments are the same.
     * Two assignments are the same if they have the same title and deadline.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        return otherAssignment != null
                && otherAssignment.getDeadline().equals(getDeadline())
                && (otherAssignment.getTitle().equals(getTitle()));
    }
}
