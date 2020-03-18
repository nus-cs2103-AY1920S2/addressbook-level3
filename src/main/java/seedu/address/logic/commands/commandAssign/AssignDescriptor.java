package seedu.address.logic.commands.commandAssign;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AssignDescriptor {

    // TODO: Change to generic type so assign is not only for " from ID to ID "
    private HashMap<Prefix, ID> IDMapping = new HashMap<Prefix, ID>();

    public AssignDescriptor() {}

    public ID getAssignID (Prefix assignEntity) {
        // TODO: Fix this
        return IDMapping.getOrDefault(assignEntity, new ID("1"));
    }

    // TODO: Throws exception
    public void setAssignEntity(Prefix assignEntity, ID assignID) {
        // We only allow 2 entities in our assignment
        if (IDMapping.entrySet().size() < 2) {
            IDMapping.put(assignEntity, assignID);
        }
    }

    public Set<Prefix> getAllAssignKeys() {
        return IDMapping.keySet();
    }

}
