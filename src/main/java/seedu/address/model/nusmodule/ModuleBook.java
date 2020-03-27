package seedu.address.model.nusmodule;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps all module-related data at the module-book level
 * Duplicates are not allowed
 */
public class ModuleBook {
    private List<NusModule> modules;

    public ModuleBook() {
        this.modules = new ArrayList<>();
    }

    public ModuleBook(List<NusModule> modules) {
        this.modules = modules;
    }

    /**
     * Adds a module to the module book.
     * The module must not already exist in the module book.
     */
    public void addModule(NusModule module) {
        this.modules.add(module);
    }

    /**
     * Removes {@code NusModule} that has the same module code as given from this {@code ModuleBook}.
     * such nus module must exist in the module book.
     */
    public void deleteModule(ModuleCode moduleCode) {
        int index = -1;
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getModuleCode().equals(moduleCode)) {
                index = i;
            }
        }
        modules.remove(index);
    }

    /**
     * Returns true if a module with the same module code as {@code NusModule} exists in the address book.
     */
    public boolean hasModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        for (NusModule module: modules) {
            if (module.getModuleCode().equals(moduleCode)) {
                return true;
            }
        }
        return false;
    }

    public double getCap() {
        Capulator capulator = new Capulator(modules);
        return capulator.calculateCap();
    }
}
