package seedu.address.model.nusmodule;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;

/**
 * Wraps all module-related data at the module-book level
 * Duplicates are not allowed
 */
public class ModuleBook {

    private List<NusModule> modules;
    private ObservableList<NusModule> modulesTakenList;
    private Major majorTaken;

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
        this.modulesTakenList.add(module);
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
        this.modulesTakenList.remove(index);
    }

    public NusModule getModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        NusModule result = null;
        for (NusModule module: modules) {
            if (module.getModuleCode().equals(moduleCode)) {
                result = module;
            }
        }
        return result;
    }

    /**
     * Updates the grade of the {@code NusModule} that has the same module code as given from this {@code ModuleBook}.
     * such nus module must exist in the module book.
     */
    public void gradeModule(ModuleCode moduleCode, Grade grade) {
        requireNonNull(moduleCode);
        requireNonNull(grade);
        for (NusModule module: modules) {
            if (module.getModuleCode().equals(moduleCode)) {
                modulesTakenList.remove(module);
                module.setGrade(grade);
                modulesTakenList.add(module);
            }
        }
    }

    /**
     * Add a new module task to a specific module recorded in our program.
     */
    public void addModuleTask(ModuleTask moduleTask) {
        requireNonNull(moduleTask);
        for (NusModule module: modules) {
            if (module.getModuleCode().equals(moduleTask.getModuleRelated())) {
                module.addTask(moduleTask);
            }
        }
    }

    /**
     * Delete a module task in a specific module recorded in our program.
     */
    public void deleteModuleTask(ModuleCode moduleCode, Index index) {
        requireNonNull(moduleCode);
        requireNonNull(index);
        getModule(moduleCode).getTasks().remove(index);
    }

    public void setMajor(Major major) {
        requireNonNull(major);
        this.majorTaken = major;
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

    public ObservableList<NusModule> getModulesTakenList() {
        modulesTakenList = FXCollections.observableArrayList(modules);
        return modulesTakenList;
    }

    public double getCap() {
        Capulator capulator = new Capulator(modules);
        return capulator.calculateCap();
    }

    public int getSizeOfModuleTaskList(ModuleCode moduleCode) {
        return getModule(moduleCode).getTasks().size();
    }
}
