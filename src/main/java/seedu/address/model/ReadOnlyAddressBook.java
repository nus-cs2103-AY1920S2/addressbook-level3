package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of the address book.
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the address book.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonsList();
}
