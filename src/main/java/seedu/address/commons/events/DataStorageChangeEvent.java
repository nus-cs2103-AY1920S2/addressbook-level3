package seedu.address.commons.events;

import seedu.address.commons.util.Constants;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

public class DataStorageChangeEvent extends BaseEvent {

    public ReadOnlyAddressBookGeneric addressBook;
    public Constants.ENTITY_TYPE entityType;

    public DataStorageChangeEvent(ReadOnlyAddressBookGeneric addressBook, Constants.ENTITY_TYPE entityType) {
        this.addressBook = addressBook;
        this.entityType = entityType;
    }

    public String toString() {
        return "Storage save event for " + entityType.toString();
    }

}
