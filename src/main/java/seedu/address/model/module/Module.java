package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * Gets group with given identifier.
     */
    public Group getGroup(String identifier) {
        Group group = null;
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

    /**
     * Returns true if both modules have the same identifiers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getIdentifier().equals(this.getIdentifier());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getIdentifier())
                .append(" has ")
                .append(groups.size())
                .append(" groups");
        return builder.toString();
    }
}
