package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.group.Group;

/**
 * Represents a module in the TAT.
 */
public class Module {
    private final String identifier;
    private final ArrayList<Group> groups;

    /**
     * Constructs a group object.
     *
     * @param identifier identifies the module. Usually equal
     *                   to the module code.
     */
    public Module(String identifier) {
        this.identifier = identifier;
        groups = new ArrayList<Group>();
    }

    //TODO: the following methods
    /**
     * Adds a group to groups.
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    //TODO: deal with no such group
    /**
     * Gets group with given identifier.
     */
    public Group getGroup(String identifier) {
        //temporary
        Group group = new Group(identifier, "lab");
        for (int i = 0; i < groups.size(); ++i) {
            group = groups.get(i);
            if (group.getIdentifier().equals(identifier)) {
                break;
            }
        }
        return group;
    }

    /**
     * Returns the group list.
     */
    public List<Group> getGroupList() {
        return groups;
    }

    /**
     * Returns the group identifier.
     */
    public String getIdentifier() {
        return identifier;
    }
}
