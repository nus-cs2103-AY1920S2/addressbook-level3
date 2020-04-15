package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;

/**
 * Unmodifiable view of the schoolwork tracker.
 */
public interface ReadOnlySchoolworkTracker {
    /**
     * Returns an unmodifiable view of the assignment list.
     * The list will not contain any duplicate assignments.
     */
    ObservableList<Assignment> getAssignmentsList();
}
