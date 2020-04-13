package seedu.address.testutil;

import seedu.address.model.ModManager;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.module.Module;

/**
 * A utility class to help with building ModManager objects.
 */
public class ModManagerBuilder {

    private ModManager modManager;

    public ModManagerBuilder() {
        modManager = new ModManager();
    }

    public ModManagerBuilder(ModManager modManager) {
        this.modManager = modManager;
    }

    /**
     * Adds a new {@code Module} to the {@code ModManager} that we are building.
     */
    public ModManagerBuilder withModule(Module module) {
        modManager.addModule(module);
        return this;
    }

    /**
     * Adds a new {@code Facilitator} to the {@code ModManager} that we are building.
     */
    public ModManagerBuilder withFacilitator(Facilitator facilitator) {
        modManager.addFacilitator(facilitator);
        return this;
    }

    public ModManager build() {
        return modManager;
    }
}
