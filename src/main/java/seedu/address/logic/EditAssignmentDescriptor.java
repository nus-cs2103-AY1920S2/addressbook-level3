package seedu.address.logic;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Status;
import seedu.address.model.assignment.Title;
import seedu.address.model.assignment.Workload;

/**
 * Stores the details to edit the assignment with. Each non-empty field value will replace the
 * corresponding field value of the assignment.
 */
public class EditAssignmentDescriptor {
    private Title title;
    private Workload workload;
    private Deadline deadline;
    private Status status;

    public EditAssignmentDescriptor() {}

    /**
     * Copy constructor.
     */
    public EditAssignmentDescriptor(EditAssignmentDescriptor toCopy) {
        setTitle(toCopy.title);
        setWorkload(toCopy.workload);
        setDeadline(toCopy.deadline);
        setStatus(toCopy.status);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(title, workload, deadline, status);
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Optional<Title> getTitle() {
        return Optional.ofNullable(title);
    }

    public void setWorkload(Workload workload) {
        this.workload = workload;
    }

    public Optional<Workload> getWorkload() {
        return Optional.ofNullable(workload);
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public Optional<Deadline> getDeadline() {
        return Optional.ofNullable(deadline);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Optional<Status> getStatus() {
        return Optional.ofNullable(status);
    }

    /**
     * Creates and returns an {@code Assignment} with the details of {@code assignmentToEdit}
     * edited with {@code editAssignmentDescriptor}.
     */
    public Assignment createEditedAssignment(Assignment assignmentToEdit) {
        assert assignmentToEdit != null;

        Title updatedTitle = getTitle().orElse(assignmentToEdit.getTitle());
        Workload updatedWorkload = getWorkload().orElse(assignmentToEdit.getWorkload());
        Deadline updatedDeadline = getDeadline().orElse(assignmentToEdit.getDeadline());
        Status updatedStatus = getStatus().orElse(assignmentToEdit.getStatus());

        return new Assignment(updatedTitle, updatedDeadline, updatedWorkload, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAssignmentDescriptor)) {
            return false;
        }

        // state check
        EditAssignmentDescriptor e = (EditAssignmentDescriptor) other;

        return getTitle().equals(e.getTitle())
            && getWorkload().equals(e.getWorkload())
            && getDeadline().equals(e.getDeadline())
            && getStatus().equals(e.getStatus());
    }
}
