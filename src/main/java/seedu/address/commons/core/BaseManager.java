package seedu.address.commons.core;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.DataStorageChangeEvent;
import seedu.address.commons.util.Constants;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

public class BaseManager {
    protected EventsCenterSingleton eventsCenterSingleton;

    public BaseManager() {
        this(EventsCenterSingleton.getInstance());
    }

    public BaseManager(EventsCenterSingleton eventsCenterSingleton) {
        this.eventsCenterSingleton = eventsCenterSingleton;
        // Register handler so all managers extending from this will subscribe to eventsCenter
        this.eventsCenterSingleton.registerHandler(this);
    }

    protected void raiseEvent(BaseEvent event) {
        eventsCenterSingleton.post(event);
    }

    protected void postDataStorageChangeEvent(ReadOnlyAddressBookGeneric addressBook,
                                            Constants.ENTITY_TYPE entityType) {
        raiseEvent(new DataStorageChangeEvent(addressBook, entityType));
    }

}
