package seedu.address.model.modelProgress;

import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSameProgress
 * comparison)
 */
public class ProgressAddressBook extends AddressBookGeneric<Progress> {

    /**
     * Creates an progress AddressBook.
     */
    public ProgressAddressBook() {
        super();
    }

    /**
     * Creates a progress AddressBook using the objects in the {@code toBeCopied}
     */
    public ProgressAddressBook(ReadOnlyAddressBookGeneric<Progress> toBeCopied) {
        super(toBeCopied);
    }

    @Override
    public String toString() {
        return objects.asUnmodifiableObservableList().size() + " progress";
    }

}
