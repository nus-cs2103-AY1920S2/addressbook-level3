package seedu.address.model.modelStaff;

import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSameStaff
 * comparison)
 */
public class StaffAddressBook extends AddressBookGeneric<Staff> {

    /**
     * Creates a Staff AddressBook.
     */
    public StaffAddressBook() {
        super();
    }

    /**
     * Creates a Staff AddressBook using the objects in the {@code toBeCopied}
     */
    public StaffAddressBook(ReadOnlyAddressBookGeneric<Staff> toBeCopied) {
        super(toBeCopied);
    }

    @Override
    public String toString() {
        return objects.asUnmodifiableObservableList().size() + " staffs";
    }

}
