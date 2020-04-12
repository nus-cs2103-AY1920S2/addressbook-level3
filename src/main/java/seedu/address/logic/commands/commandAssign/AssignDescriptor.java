package seedu.address.logic.commands.commandAssign;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.modelObjectTags.ID;

import java.util.HashMap;
import java.util.Set;

public class AssignDescriptor {

    // TODO: Change to generic type so assign is not only for " from ID to ID "
    private HashMap<Prefix, ID> IDMapping = new HashMap<Prefix, ID>();

    public AssignDescriptor() {
    }

    public ID getAssignID(Prefix assignEntity) throws CommandException {
        if (IDMapping.containsKey(assignEntity)) {
            return IDMapping.get(assignEntity);
        } else {
            throw new CommandException("No ID exists for this Prefix");
        }
    }

    // TODO: Throws exception
    // TODO: Consider if checking should happen if assign descriptor is > 2
    public void setAssignEntity(Prefix assignEntity, ID assignID) {
        IDMapping.put(assignEntity, assignID);
    }

    public HashMap<Prefix, ID> getIDMapping() {
        return this.IDMapping;
    }

    public Set<Prefix> getAllAssignKeys() {
        return IDMapping.keySet();
    }

    public Prefix[] getType() {
        Prefix firstPrefix = (Prefix) IDMapping.keySet().toArray()[0];
        Prefix secondPrefix = (Prefix) IDMapping.keySet().toArray()[1];
        return new Prefix[]{firstPrefix, secondPrefix};
    }
}
