package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;

/**
 * Unmodifiable view of the scheduler.
 */
public interface ReadOnlyScheduler {
    /**
     * Returns an unmodifiable view of the assignment list.
     * This list will not contain any duplicate assignments.
     */
    ObservableList<Assignment> getAssignmentsList();
}
