package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.person.Student;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons and modules list.
     * This list will not contain any duplicate persons or modules.
     */
    ObservableList<Student> getPersonList();
    ObservableList<Module> getModuleList();

}
