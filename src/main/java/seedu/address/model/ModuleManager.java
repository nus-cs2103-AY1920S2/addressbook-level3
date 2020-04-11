package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;

//@@author gyant6
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

    public boolean hasModule(ModuleCode moduleCode) {
        return moduleList.hasModuleWithModuleCode(moduleCode);
    }

    public boolean hasModules(Set<ModuleCode> moduleCodes) {
        return moduleCodes.stream().allMatch(this::hasModule);
    }

    public Module getModule(ModuleCode moduleCode) {
        return moduleList.getModuleWithModuleCode(moduleCode);
    }

    public Set<Module> getModules(Set<ModuleCode> moduleCodes) {
        return moduleCodes.stream().map(this::getModule).collect(Collectors.toSet());
    }

    public ModuleList getModuleList() {
        return moduleList;
    }
}
