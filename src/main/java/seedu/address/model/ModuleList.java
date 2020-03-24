package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.course.module.Module;
import seedu.address.model.profile.course.module.ModuleCode;

/**
 * Creates a new ModuleList object which contains Module objects.
 */
public class ModuleList {

    private ArrayList<Module> moduleList = new ArrayList<>();
    private ArrayList<ModuleCode> moduleCodes = new ArrayList<>();

    public ModuleList() {}

    /**
     * Adds a module to the module list.
     */
    public void addModule(Module module) {
        moduleList.add(module);
        moduleCodes.add(module.getModuleCode());
    }

    /**
     * Returns true if a module with the same fields as {@code module} exists in the module list.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return moduleList.contains(module);
    }

    /**
     * Returns true if a module with the module code {@code moduleCode} exists in the module list.
     */
    public boolean hasModuleWithModuleCode(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return moduleCodes.contains(moduleCode);
    }

    /**
     * Returns the module with module code {@code moduleCode} in the module list, if it exists.
     * @throws NoSuchElementException No module in the module list contains {@code moduleCode}.
     */
    public Module getModuleWithModuleCode(ModuleCode moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        if (!hasModuleWithModuleCode(moduleCode)) {
            throw new ParseException(MESSAGE_INVALID_MODULE);
        }
        for (Module mod: moduleList) {
            if (mod.getModuleCode().equals(moduleCode)) {
                return mod;
            }
        }
        // Code should not reach this line
        assert false;
        return null;
    }
}
