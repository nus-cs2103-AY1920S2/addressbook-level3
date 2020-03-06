package seedu.address.model.profile.course;

import java.util.ArrayList;

/**
 * Represents a list of modules in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ModuleSet {

    private final ArrayList<Module> moduleSet; // can use TreeSet to sort modules by code

    /**
     * Constructs a {@code ModuleSet}.
     */
    public ModuleSet() {
        moduleSet = new ArrayList<>();
    }

    public ArrayList<Module> getModuleSet() {
        return moduleSet;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModuleSet)) {
            return false;
        }

        ModuleSet otherSet = (ModuleSet) other;
        for (Module module : moduleSet) {
            for (Module otherModule : otherSet.getModuleSet()) {
                if (!module.equals(otherModule)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Methods to be implemented
    // isSameSet()
}
