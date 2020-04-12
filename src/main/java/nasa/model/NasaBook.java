package nasa.model;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nasa.commons.core.index.Index;
import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.UniqueDeadlineList;
import nasa.model.activity.UniqueEventList;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.UniqueModuleList;

/**
 * Wraps all data at the Nasa Book Level
 * Duplicates are not allowed (by .isSameEvent comparison)
 */
public class NasaBook implements ReadOnlyNasaBook {

    private final UniqueModuleList moduleList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        moduleList = new UniqueModuleList();
    }

    public NasaBook() {
    }

    /**
     * Creates a NasaBook using the Modules in the {@code toBeCopied}
     *
     * @param toBeCopied ReadOnlyNasaBook
     */
    public NasaBook(ReadOnlyNasaBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Get the {@code UniqueEventList} of module {@code moduleCode} with
     *
     * @param moduleCode ModuleCode
     * @return UniqueEventList
     */
    public UniqueEventList getEvents(ModuleCode moduleCode) {
        return moduleList.getModule(moduleCode).getEventList();
    }

    /**
     * Get the contents of the activities of module {@code moduleCode} with {@code activities}
     *
     * @param moduleCode ModuleCode
     * @return UniqueDeadlineList
     */
    public UniqueDeadlineList getDeadlines(ModuleCode moduleCode) {
        return moduleList.getModule(moduleCode).getDeadlineList();
    }

    /**
     * Replaces current module list with another module list.
     *
     * @param moduleList must not be empty
     */
    public void setModuleList(UniqueModuleList moduleList) {
        requireNonNull(moduleList);

        this.moduleList.setModules(moduleList);
    }

    public void setModuleList(List<Module> moduleList) {
        requireNonNull(moduleList);

        this.moduleList.setModules(moduleList);
    }


    /**
     * Add a single event to module {@code moduleCode} with {@code event}
     * {@code event} must not contain duplicate activities.
     *
     * @param event      Event
     * @param moduleCode ModuleCode
     */
    public void addEvent(ModuleCode moduleCode, Event event) {
        requireNonNull(event);

        Module toEditModule = moduleList.getModule(moduleCode);
        toEditModule.addEvent(event);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exit in the NasaBook.
     * The event identity of {@code editedEvent} must not be the same as another existing event in Nasa Book.
     *
     * @param target      Event
     * @param editedEvent Event
     */
    public void setEvent(ModuleCode moduleCode, Event target, Event editedEvent) {
        requireAllNonNull(moduleCode, target, editedEvent);

        UniqueEventList eventList = getEvents(moduleCode);
        assert eventList != null;
        eventList.setActivity(target, editedEvent);
    }

    /**
     * Remove a single event from module code with {@code moduleCode} with {@code event}
     * {@code event} must exist in the list.
     *
     * @param event      Event
     * @param moduleCode ModuleCode
     */
    public void removeEvent(ModuleCode moduleCode, Event event) {
        requireNonNull(event);

        Module toEditModule = moduleList.getModule(moduleCode);
        toEditModule.removeEvent(event);
    }

    /**
     * Add a single deadline to module {@code moduleCode} with {@code deadline}
     * {@code deadline} must not contain duplicate activities.
     *
     * @param deadline   Deadline
     * @param moduleCode ModuleCode
     */
    public void addDeadline(ModuleCode moduleCode, Deadline deadline) {
        requireNonNull(deadline);

        Module toEditModule = moduleList.getModule(moduleCode);
        toEditModule.addDeadline(deadline);
    }

    /**
     * Replaces the given deadline {@code target} in the list with {@code editedDeadline}.
     * {@code target} must exit in the NasaBook.
     * The deadline identity of {@code editedDeadline} must not be the same as another existing deadline in Nasa Book.
     *
     * @param target         Deadline
     * @param editedDeadline Deadline
     */
    public void setDeadline(ModuleCode moduleCode, Deadline target, Deadline editedDeadline) {
        requireAllNonNull(moduleCode, target, editedDeadline);
        UniqueDeadlineList deadlineList = getDeadlines(moduleCode);
        assert deadlineList != null;
        deadlineList.setActivity(target, editedDeadline);
    }

    /**
     * Remove a single deadline from module {@code module} with {@code deadline}
     * {@code deadline} must exist in the list.
     *
     * @param deadline   Deadline
     * @param moduleCode ModuleCode
     */
    public void removeDeadline(ModuleCode moduleCode, Deadline deadline) {
        requireNonNull(deadline);

        Module toEditModule = moduleList.getModule(moduleCode);
        toEditModule.removeDeadline(deadline);
    }


    /**
     * Resets the existing data of this {@code NasaBook} with {@code newData}
     *
     * @param newData ReadOnlyNasaBook
     */
    public void resetData(ReadOnlyNasaBook newData) {
        requireNonNull(newData);

        moduleList.setModules(newData.getModuleList());
    }

    //// module-Level operations

    /**
     * Returns true if an module has the same identity as {@code module} exits in NasaBook.
     *
     * @param moduleCode ModuleCode
     * @return boolean
     */
    public boolean hasModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return moduleList.contains(moduleCode);
    }

    /**
     * Adds a module to the NasaBook.
     * The module must not already exist in the NasaBook
     *
     * @param module Module
     */
    public void addModule(Module module) {
        moduleList.add(module);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exit in the NasaBook.
     * The module identity of {@code editedModule} must not be the same as another existing module in Nasa Book.
     *
     * @param target       Module
     * @param editedModule Module
     */
    public void setModule(ModuleCode target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        moduleList.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code NasaBook}.
     * {@code key} must exist in the Nasa Book.
     *
     * @param key ModuleCode
     */
    public void removeModule(ModuleCode key) {
        moduleList.remove(key);
    }

    public UniqueModuleList getList() {
        return moduleList;
    }

    /**
     * Return a new NasaBook, to avoid pointing to the same data when testing.
     *
     * @return NasaBook
     */
    public NasaBook deepCopyNasaBook() {
        NasaBook newNasaBook = new NasaBook();
        newNasaBook.setModuleList(getDeepCopyList());
        return newNasaBook;
    }

    /**
     * Reschedule all activity based on user presets.
     */
    public void scheduleAll() {
        moduleList.asModifiableObservableList()
                .forEach(x -> {
                    x.getModifiableDeadlineList()
                            .forEach(Deadline::regenerate);
                    x.getModifiableEventList()
                            .forEach(Event::regenerate);
                });
    }

    public boolean setDeadlineSchedule(ModuleCode module, Index index, Index type) {
        if (hasModule(module)) {
            Module item = moduleList.getModule(module);
            item.setDeadlineSchedule(index, type);
            return true;
        }
        return false;
    }

    public boolean setEventSchedule(ModuleCode module, Index index, Index type) {
        if (hasModule(module)) {
            Module item = moduleList.getModule(module);
            item.setEventSchedule(index, type);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return moduleList.asUnmodifiableObservableList().size() + " moduleList";
        //TODO: refine Later
    }

    @Override
    public UniqueModuleList getUniqueModuleList() {
        return moduleList;
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return moduleList.asUnmodifiableObservableList();
    }

    /**
     * Ensure that the class being extracted does not points to the same object.
     */
    @Override
    public ObservableList<Module> getDeepCopyList() {
        ObservableList<Module> deepCopyList = FXCollections.observableArrayList();
        for (Module mods : moduleList.asUnmodifiableObservableList()) {
            deepCopyList.add(mods.getDeepCopyModule());
        }
        return deepCopyList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof NasaBook
                && moduleList.equals(((NasaBook) other).moduleList));
    }

    @Override
    public int hashCode() {
        return moduleList.hashCode();
    }

    public Module getModule(ModuleCode moduleCode) {
        return moduleList.getModule(moduleCode);
    }

    /**
     * Returns true if activity is found in the module with {@code moduleCode}
     * @param moduleCode module code of the module to be searched
     * @param activity activity to be searched
     * @return true if activity found in the module, else false
     */
    public boolean hasActivity(ModuleCode moduleCode, Activity activity) {
        Module module = getModule(moduleCode);
        return module.hasActivity(activity);
    }
}
