package seedu.address.model;

import seedu.address.model.assignment.Assignment;

import java.util.ArrayList;

/**
 * Unmodifiable view of the scheduler.
 */
public interface ReadOnlyScheduler {

    /**
     * Returns an unmodifiable view of the assignment list.
     * This list will not contain any duplicate assignments.
     */
     ArrayList<Assignment> getAssignmentsList();
}
