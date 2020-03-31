package seedu.address.commons.events;

import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

public class DataStorageChangeEvent extends BaseEvent {

    public ReadOnlyAddressBookGeneric addressBook;

    public DataStorageChangeEvent(ReadOnlyAddressBookGeneric addressBook) {
        this.addressBook = addressBook;
    }

}
