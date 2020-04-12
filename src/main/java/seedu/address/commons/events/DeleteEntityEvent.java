package seedu.address.commons.events;

import seedu.address.commons.util.Constants;
import seedu.address.model.modelObjectTags.ID;

public class DeleteEntityEvent extends BaseEvent {
    public Constants.ENTITY_TYPE entityType;
    public ID entityID;

    public DeleteEntityEvent(ID entityID, Constants.ENTITY_TYPE entityType) {
        this.entityType = entityType;
        this.entityID = entityID;
    }

    public String toString() {
        return "Delete entity" + entityType.toString();
    }

}
