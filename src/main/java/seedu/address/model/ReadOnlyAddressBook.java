package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
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
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Returns an unmodifiable view of the sessions list.
     * This list will not contain any duplicate sessions.
     */
    ObservableList<Session> getSessionList();
}
