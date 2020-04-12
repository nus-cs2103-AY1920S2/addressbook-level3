package seedu.address.model.modelAssignment;

import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson
 * comparison)
 */
public class AssignmentAddressBook extends AddressBookGeneric<Assignment> {

    /**
     * Creates an Assignment AddressBook.
     */
    public AssignmentAddressBook() {
        super();
    }

    /**
     * Creates an Assignment AddressBook using the objects in the {@code toBeCopied}
     */
    public AssignmentAddressBook(ReadOnlyAddressBookGeneric<Assignment> toBeCopied) {
        super(toBeCopied);
    }

    @Override
    public String toString() {
        return objects.asUnmodifiableObservableList().size() + " assignments";
        // TODO: refine later
    }
}
