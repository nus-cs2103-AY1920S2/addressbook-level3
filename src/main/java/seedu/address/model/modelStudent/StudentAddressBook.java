package seedu.address.model.modelStudent;

import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSameStudent
 * comparison)
 */
public class StudentAddressBook extends AddressBookGeneric<Student> {
    public StudentAddressBook() {
        super();
    }

    /**
     * Creates an AddressBook using the Students in the {@code toBeCopied}
     */
    public StudentAddressBook(ReadOnlyAddressBookGeneric<Student> toBeCopied) {
        super(toBeCopied);
    }

    @Override
    public String toString() {
        return objects.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }
}
