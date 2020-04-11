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
        this.status = new Status(Status.ASSIGNMENT_OUTSTANDING);
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
        builder.append(getTitle())
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
                && otherAssignment.getDeadline().dateTime.compareTo(getDeadline().dateTime) == 0
                && (otherAssignment.getTitle().equals(getTitle()));
    }

    /**
     * Returns true if both assignments have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherAssignment = (Assignment) other;
        return otherAssignment.getTitle().equals(getTitle())
                && otherAssignment.getStatus().equals(getStatus())
                && otherAssignment.getWorkload().equals(getWorkload())
                && otherAssignment.getDeadline().dateTime.compareTo(getDeadline().dateTime) == 0;
    }
}
