package seedu.address.model.modelProgress;

import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

public class ProgressAddressBook extends AddressBookGeneric<Progress> {

    public ProgressAddressBook() {
        super();
    }

    public ProgressAddressBook(ReadOnlyAddressBookGeneric<Progress> toBeCopied) {
        super(toBeCopied);
    }

    @Override
    public String toString() {
        return objects.asUnmodifiableObservableList().size() + " progress";
    }

}
