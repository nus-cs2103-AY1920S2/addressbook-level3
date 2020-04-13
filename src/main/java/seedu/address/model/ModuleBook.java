package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.nusmodule.Capulator;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.Major;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.NusModule;

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

        modulesTakenList = FXCollections.observableList(modules);
    }

    /**
     * Creates an ModuleBook using the Modules in the {@code toBeCopied}
     */
    public ModuleBook(ModuleBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public ModuleBook(List<NusModule> modules) {
        this.modules = modules;
        getModulesTakenList();
    }

    /**
     * Adds a module to the module book.
     * The module must not already exist in the module book.
     */
    public void addModule(NusModule module) {

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
     * Replaces the contents of the module list with {@code mdoules}.
     */
    public void setModules(List<NusModule> modules) {
        this.modules = modules;
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ModuleBook newData) {
        requireNonNull(newData);

        setModules(newData.getModulesTakenList());


    }

    /**
     * Up
     * ydates the grade of the {@code NusModule} that has the same module code as given from this {@code ModuleBook}.
     * such nus module must exist in the module book.
     */
    public void gradeModule(ModuleCode moduleCode, Grade grade) {
        requireNonNull(moduleCode);
        requireNonNull(grade);
        NusModule targetModule = null;
        for (NusModule module: modules) {
            if (module.getModuleCode().equals(moduleCode)) {
                targetModule = module;
            }
        }
        modulesTakenList.remove(targetModule);
        targetModule.setGrade(grade);
        modulesTakenList.add(targetModule);
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
        getModule(moduleCode).getTasks().remove(index.getZeroBased());
    }

    /**
     * Mark a module task as done in a specific module recorded in our program.
     */
    public void doneModuleTask(ModuleCode moduleCode, Index index) {
        requireNonNull(moduleCode);
        requireNonNull(index);
        getModule(moduleCode).getTasks().get(index.getZeroBased()).markAsDone();
    }

    /**
     * Remove a specific module recorded in our program.
     */
    public void removeModuleTask(ModuleTask moduleTask) {
        NusModule targetModule = null;
        ModuleTask targetTask;
        for (NusModule module: modules) {
            for (ModuleTask mt : module.getTasks()) {
                if (mt.equals(moduleTask)) {
                    targetModule = module;
                    targetTask = moduleTask;
                }
            }
        }
        targetModule.getTasks().remove(moduleTask);
    }

    public List<ModuleTask> getModuleTaskList(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return getModule(moduleCode).getTasks();
    }

    /**
     * Return a String contains the information of the tasks related to the specified module.
     */
    public String getModuleTaskInfo(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        String infoOfTasks = "";
        List<ModuleTask> tasks = getModuleTaskList(moduleCode);
        for (int i = 0; i < tasks.size(); i++) {
            String task = (i + 1) + ". " + tasks.get(i) + "\n";
            infoOfTasks += task;
        }
        return infoOfTasks;
    }

    /**
     * Return a String contains the information of how many tasks are there related to each module in module book.
     */
    public String getTaskBreakdown() {
        String infoOfTasks = "";
        for (NusModule module: modules) {
            int numberOfTasksNotDone = 0;
            for (ModuleTask moduleTask: module.getTasks()) {
                if (moduleTask.getDoneStatus() == 0) {
                    numberOfTasksNotDone += 1;
                }
            }
            String message = "- " + module.getModuleCode() + ":   "
                    + module.getTasks().size() + " tasks in total   "
                    + numberOfTasksNotDone + " tasks haven't been completed\n";
            infoOfTasks += message;
        }
        return infoOfTasks;
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
