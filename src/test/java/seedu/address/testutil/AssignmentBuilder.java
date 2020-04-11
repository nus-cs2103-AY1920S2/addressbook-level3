package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Status;
import seedu.address.model.assignment.Title;
import seedu.address.model.assignment.Workload;

/**
 * A utility class to help with building {@code Assignment} objects.
 */
public class AssignmentBuilder {
    public static final String DEFAULT_TITLE = "CS2103 tP";
    public static final String DEFAULT_DEADLINE = "2020-11-17 23:59";
    public static final String DEFAULT_WORKLOAD = "100";
    public static final String DEFAULT_STATUS = "Uncompleted";

    private Title title;
    private Deadline deadline;
    private Workload estHours;
    private Status status;

    /**
     * Initialises AssignmentBuilder with the data of {@Assignment toCopy}.
     */
    public AssignmentBuilder(Assignment toCopy) {
        this.title = toCopy.getTitle();
        this.deadline = toCopy.getDeadline();
        this.estHours = toCopy.getWorkload();
        this.status = toCopy.getStatus();
    }

    public AssignmentBuilder() {
        this.title = new Title(DEFAULT_TITLE);
        this.deadline = new Deadline(DEFAULT_DEADLINE);
        this.estHours = new Workload(DEFAULT_WORKLOAD);
        this.status = new Status(DEFAULT_STATUS);
    }

    /**
     * Sets the {@Title} of the {@Assignment} that we are building.
     */
    public AssignmentBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@Deadline} of the {@Assignment} that we are building.
     */
    public AssignmentBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    /**
     * Sets the {@Workload} of the {@Assignment} that we are building.
     */
    public AssignmentBuilder withWorkload(String estHours) {
        this.estHours = new Workload(estHours);
        return this;
    }

    /**
     * Sets the {@Status} of the {@Assignment} that we are building.
     */
    public AssignmentBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    public Assignment build() {
        return new Assignment(title, deadline, estHours, status);
    }
}
