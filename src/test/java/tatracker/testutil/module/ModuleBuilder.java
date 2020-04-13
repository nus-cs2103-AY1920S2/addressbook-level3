//@@author aakanksha-rai

package tatracker.testutil.module;

import tatracker.model.group.Group;
import tatracker.model.group.UniqueGroupList;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.session.UniqueSessionList;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_NAME = "Software Engineering";
    public static final String DEFAULT_MODULE_CODE = "CS2103T";

    private String name;
    private String identifier;
    private UniqueGroupList groups;
    private UniqueSessionList doneSessions;

    public ModuleBuilder() {
        this.name = DEFAULT_NAME;
        this.identifier = DEFAULT_MODULE_CODE;
        this.groups = new UniqueGroupList();
        this.doneSessions = new UniqueSessionList();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        name = moduleToCopy.getName();
        identifier = moduleToCopy.getIdentifier();
        groups = moduleToCopy.getUniqueGroupList();
    }

    /**
     * Sets the {@code Name} of the {@code Module} that we are building.
     */
    public ModuleBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code identifier} of the {@code Module} that we are building.
     */
    public ModuleBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    /**
     * Adds the {@code group} to the {@code Module} that we are building.
     */
    public ModuleBuilder withGroup(Group group) {
        groups.add(group);
        return this;
    }

    /**
     * Adds the {@code session} to the {@code Module} that we are building.
     */
    public ModuleBuilder withDoneSession(Session session) {
        doneSessions.add(session);
        return this;
    }

    public Module build() {
        return new Module(identifier, name, groups);
    }

}
