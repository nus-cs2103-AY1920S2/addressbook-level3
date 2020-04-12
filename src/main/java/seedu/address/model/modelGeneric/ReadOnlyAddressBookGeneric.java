package seedu.address.model.modelGeneric;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBookGeneric<K extends ModelObject> {

    /**
     * Returns an unmodifiable view of the teachers list. This list will not contain any duplicate
     * teachers.
     */
    ObservableList<K> getList();

}
