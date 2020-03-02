package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.entry.Entry;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of a log book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the entry list.
     * This list will not contain any duplicate entries.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the entry list.
     * This list will not contain any duplicate entries.
     */
    ObservableList<Entry> getEntryList();
}

