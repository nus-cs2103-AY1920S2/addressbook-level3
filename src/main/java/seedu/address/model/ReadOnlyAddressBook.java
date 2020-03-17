package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.session.Session;
import seedu.address.model.student.Student;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the student list.
     * This list will not contain any duplicate student.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the session list.
     * This list will not contain any duplicate session.
     */
    ObservableList<Session> getSessionList();
}
