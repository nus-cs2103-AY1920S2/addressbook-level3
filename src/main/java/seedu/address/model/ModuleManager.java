package seedu.address.model;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Represents the in-memory model of the module list data.
 */
public class ModuleManager {

    private static ModuleList moduleList;

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

    public static Module getModule(ModuleCode moduleCode) throws ParseException {
        return moduleList.getModuleWithModuleCode(moduleCode);
    }
}
