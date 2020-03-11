package NASA.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import NASA.commons.core.index.Index;
import NASA.model.activity.Note;
import javafx.collections.ObservableList;
import NASA.model.activity.Activity;
import NASA.model.activity.UniqueActivityList;
import NASA.model.module.Module;
import NASA.model.module.UniqueModuleList;

/**
 * Wraps all data at the Nasa Book Level
 * Duplicates are not allowed (by .isSameActivity comparison)
 */
public class NasaBook implements ReadOnlyNasaBook {

    private final UniqueModuleList moduleList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        moduleList = new UniqueModuleList();
    }

    public NasaBook() {}

    /**
     * Creates a NasaBook using the moduleList in the {@code toBeCopied}
     */
    public NasaBook(ReadOnlyNasaBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the activities of module {@moduleCode} with {@code activities}
     * {@code activities} must not contain duplicate activities.
     */
    public UniqueActivityList getActivities(Module module) {
        return moduleList.getActivities(module);
    }

    /**
     * Replaces the contents of the activities of module {@moduleCode} with {@code activities}
     * {@code activities} must not contain duplicate activities.
     */
    public void setActivities(Module module, List<Activity> activities) {
        Module toEditModule = moduleList.getModule(module);
        toEditModule.setActivities(activities);
        moduleList.setModule(module, toEditModule);
    }

    /**
     * Add a single activity to module {@moduleCode} with {@code activity}
     * {@code activity} must not contain duplicate activities.
     */
    public void addActivity(Module module, Activity activity) {
        requireNonNull(activity);

        Module toEditModule = moduleList.getModule(module);
        toEditModule.add(activity);
        moduleList.setModule(module, toEditModule);
    }

    /**
     * Remove a single activity from module {@code module} with {@code activity}
     * {@code activity} must exist in the list.
     */
    public void removeActivity(Module module, Activity activity) {
        requireNonNull(activity);

        Module toEditModule = moduleList.getModule(module);
        toEditModule.remove(activity);
        moduleList.setModule(module, toEditModule);
    }

    /**
     * Check if it has activity {@code activity} in {@code module}
     */
    public boolean hasActivity(Module module, Activity activity) {
        requireNonNull(activity);

        Module toEditModule = moduleList.getModule(module);
        return toEditModule.contains(activity);
    }

    /**
     * Resets the existing data of this {@code NasaBook} with {@code newData}
     */
    public void resetData(ReadOnlyNasaBook newData) {
        requireNonNull(newData);

        moduleList.setModules(newData.getModuleList());
    }

    //// module-Level operations

    /**
     * Returns true if an module has the same identity as {@code module} exits in NasaBook.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return moduleList.contains(module);
    }

    /**
     * Adds an module to the NasaBook.
     * The module must not already exist in the NasaBook
     */
    public void addModule(Module module) {
        moduleList.add(module);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedActivity}.
     * {@code target} must exit in the NasaBook.
     * The module identity of {@code editedActivity} must not be the same as another existing module in Nasa Book.
     */
    public void setModule(Module target, Module editedActivity) {
        requireNonNull(editedActivity);

        moduleList.setModule(target, editedActivity);
    }

    public void setActivityByIndex(Module module, Index index, Activity activity) {
        requireNonNull(activity);

        moduleList.setActivityByIndex(module, index, activity);
    }

    public void editActivityByIndex(Module module, Index index, Object... args) {
        requireNonNull(args);

        moduleList.editActivityByIndex(module, index, args);
    }

    /**
     * Removes {@code key} from this {@code NasaBook}.
     * {@code key} must exist in the Nasa Book.
     */
    public void removeModule(Module key) {
        moduleList.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return moduleList.asUnmodifiableObservableList().size() + " moduleList";
        //TODO: refine Later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return moduleList.asUnmodifiableObservableList();
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
}
