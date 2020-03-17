package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.person.Student;
import seedu.address.model.session.Session;


/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Student> getPersonList();

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
