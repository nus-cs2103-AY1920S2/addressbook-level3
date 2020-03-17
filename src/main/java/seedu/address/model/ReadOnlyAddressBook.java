package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.person.Student;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons.
     * This list will not contain any duplicate persons.
     * Also returns an unmodifiable view of the modules.
     * This list will not conatin any duplicate modules.
     */
    ObservableList<Student> getPersonList();
    ObservableList<Module> getModuleList();

}
