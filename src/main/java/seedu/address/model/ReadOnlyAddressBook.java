package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Student;
import seedu.address.model.session.Session;


/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the person list.
     * This list will not contain any duplicate person.
     */
    ObservableList<Student> getPersonList();

    /**
     * Returns an unmodifiable view of the session list.
     * This list will not contain any duplicate session.
     */
    ObservableList<Session> getSessionList();
}
