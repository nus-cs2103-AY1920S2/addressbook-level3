package nasa.model.module;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nasa.commons.core.index.Index;
import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.module.exceptions.DuplicateModuleException;
import nasa.model.module.exceptions.ModuleNotFoundException;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A Module is considered unique by comparing using {@code Module#equals(Module)}.
 * As such, adding and updating of Module uses Module#equals(Module)
 * for equality so as to ensure that the Module being added or updated is
 * unique in terms of identity in the UniqueModuleList. However, the removal of a Module uses
 * Module#equals(Object) so as to ensure that the Module with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueModuleList implements Iterable<Module> {

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();
    private final ObservableList<Module> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent ModuleCode as the given argument.
     * Note: Underlying implementation of equality check on ModuleCode is case-insensitive.
     * @param toCheck ModuleCode
     * @return boolean
     */
    public boolean contains(ModuleCode toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(x -> x.getModuleCode().equals(toCheck));
    }

    /**
     * Adds a Module to the list.
     * The Module must not already exist in the list.
     * @param toAdd Module
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd.getModuleCode())) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the ModuleCode {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The ModuleCode identity of {@code editedModule} must not be the same as another existing Module in the list.
     * @param target ModuleCode
     * @param editedModule Module
     */
    public void setModule(ModuleCode target, Module editedModule) {
        requireAllNonNull(target, editedModule);
        ModuleCode editedModuleCode = editedModule.getModuleCode();
        int index = internalList.indexOf(getModule(target));
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        // case when editedModule is a non-target module that already exists in { @code UniqueModuleList }
        if (!target.equals(editedModuleCode) && contains(editedModuleCode)) {
            throw new DuplicateModuleException();
        }

        internalList.set(index, editedModule);
    }

    /**
     * Removes the equivalent ModuleCode from the list.
     * The ModuleCode must exist in the list.
     * @param toRemove ModuleCode
     */
    public void remove(ModuleCode toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(getModule(toRemove))) {
            throw new ModuleNotFoundException();
        }
    }

    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     * @param modules List
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    /**
     * get a particular module from the list
     * @param moduleCode ModuleCode
     * @return Module
     */
    public Module getModule(ModuleCode moduleCode) {
        requireAllNonNull(moduleCode);
        return internalList.stream()
                .filter(x -> x.getModuleCode().equals(moduleCode))
                .findFirst()
                .orElse(null);
    }

    public void setDeadlineSchedule(ModuleCode moduleCode, Index index, Index type) {
        Module moduleSelected = getModule(moduleCode);
        moduleSelected.setDeadlineSchedule(index, type);
        moduleSelected.updateFilteredActivityList(x -> true);
    }

    public void setEventSchedule(ModuleCode moduleCode, Index index, Index type) {
        Module moduleSelected = getModule(moduleCode);
        moduleSelected.setEventSchedule(index, type);
        moduleSelected.updateFilteredActivityList(x -> true);
    }

    public ObservableList<Module> getDeepCopyList() {
        ObservableList<Module> deepCopyList = FXCollections.observableArrayList();
        for (Module mods : internalUnmodifiableList) {
            Module moduleTemp = new Module(mods.getModuleCode(), mods.getModuleName());
            ObservableList<Deadline> deadlinesCopy = FXCollections.observableArrayList();
            ObservableList<Activity> deadlines = mods.getDeepCopyDeadlineList();
            for (Activity activity : deadlines) {
                deadlinesCopy.add((Deadline) activity);
            }
            moduleTemp.setDeadlines(deadlinesCopy);
            ObservableList<Event> eventsCopy = FXCollections.observableArrayList();
            ObservableList<Activity> events = mods.getDeepCopyEventList();
            for (Activity activity : events) {
                eventsCopy.add((Event) activity);
            }
            moduleTemp.setEvents(eventsCopy);
            deepCopyList.add(moduleTemp);
        }
        return deepCopyList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     * @return ObservableList
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public ObservableList<Module> asModifiableObservableList() {
        return internalList;
    }

    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleList // instanceof handles nulls
                && internalList.equals(((UniqueModuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code modules} contains only unique modules.
     * @param modules List
     * @return boolean
     */
    private boolean modulesAreUnique(List<Module> modules) {
        for (int i = 0; i < modules.size() - 1; i++) {
            for (int j = i + 1; j < modules.size(); j++) {
                if (modules.get(i).equals(modules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
