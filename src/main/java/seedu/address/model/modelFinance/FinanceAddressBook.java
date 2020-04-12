package seedu.address.model.modelFinance;

import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson
 * comparison)
 */
public class FinanceAddressBook extends AddressBookGeneric<Finance> {

    /**
     * Creates a Finance AddressBook.
     */
    public FinanceAddressBook() {
        super();
    }

    /**
     * Creates an Finance AddressBook using the objects in the {@code toBeCopied}
     */
    public FinanceAddressBook(ReadOnlyAddressBookGeneric<Finance> toBeCopied) {
        super(toBeCopied);
    }

    @Override
    public String toString() {
        return objects.asUnmodifiableObservableList().size() + " finance";
    }
}
