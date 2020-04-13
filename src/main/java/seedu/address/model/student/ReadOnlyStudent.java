package seedu.address.model.student;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a student list
 */
public interface ReadOnlyStudent {

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Student> getStudentList();

}
