package seedu.address.model;

import static java.util.Objects.requireNonNull;

import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Represents the in-memory model of the module list data.
 */
public class ModuleManager {

    private final ModuleList moduleList;

    public ModuleManager(ModuleList moduleList) {
        requireNonNull(moduleList);

        this.moduleList = moduleList;
    }

    public ModuleManager() {
        this(new ModuleList());
    }

    public boolean hasModule(ModuleCode moduleCode) {
        return this.moduleList.hasModuleWithModuleCode(moduleCode);
    }

    public Module getModule(ModuleCode moduleCode) {
        return this.moduleList.getModuleWithModuleCode(moduleCode);
    }
}
