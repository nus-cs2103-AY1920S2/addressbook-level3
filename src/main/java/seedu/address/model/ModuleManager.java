package seedu.address.model;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Represents the in-memory model of the module list data.
 */
public class ModuleManager {

    private ModuleList moduleList;

    public ModuleManager(ModuleList moduleList) {
        requireNonNull(moduleList);

        this.moduleList = moduleList;
    }

    public ModuleManager() {
        this(new ModuleList());
    }

    public boolean hasModule(ModuleCode moduleCode) throws CommandException {
        return moduleList.hasModuleWithModuleCode(moduleCode);
    }

    public Module getModule(ModuleCode moduleCode) {
        return moduleList.getModuleWithModuleCode(moduleCode);
    }
}
