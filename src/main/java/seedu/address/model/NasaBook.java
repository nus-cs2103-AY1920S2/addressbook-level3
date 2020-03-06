package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.UniqueActivityList;

/**
 * Wraps all data at the Nasa Book Level
 * Duplicates are not allowed (by .isSameActivity comparison)
 */
public class NasaBook implements ReadOnlyNasaBook {

    private final UniqueActivityList activities;
    //private final UniqueModuleList modules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        activities = new UniqueActivityList();
        //modules = new UniqueModuleList();
    }

    public NasaBook() {}

    /**
     * Creates a NasaBook using the Activities in the {@code toBeCopied}
     */
    public NasaBook(ReadOnlyNasaBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the activities with {@code activities}
     * {@code activities} must not contain duplicate activities
     */
    public void setActivities(List<Activity> activities) {
        this.activities.setActivities(activities);
    }

    /**
     * Resets the existing data of this {@code NasaBook} with {@code newData}
     */
    public void resetData(ReadOnlyNasaBook newData) {
        requireNonNull(newData);

        setActivities(newData.getActivityList());
    }

    //// activity-Level operations

    /**
     * Returns true if an activity has the same identity as {@code activity} exits in NasaBook.
     */
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return activities.contains(activity);
    }

    /**
     * Adds an activity to the NasaBook.
     * The activity must not already exist in the NasaBook
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * Replaces the given activity {@code target} in the list with {@code editedActivity}.
     * {@code target} must exit in the NasaBook.
     * The activity identity of {@code editedActivity} must not be the same as another existing activity in Nasa Book.
     */
    public void setActivity(Activity target, Activity editedActivity) {
        requireNonNull(editedActivity);

        activities.setActivity(target, editedActivity);
    }

    /**
     * Removes {@code key} from this {@code NasaBook}.
     * {@code key} must exist in the Nasa Book.
     */
    public void removeActivity(Activity key) {
        activities.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return activities.asUnmodifiableObservableList().size() + " activities";
        //TODO: refine Later
    }

    @Override
    public ObservableList<Activity> getActivityList() {
        return activities.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof NasaBook
                && activities.equals(((NasaBook) other).activities));
    }

    @Override
    public int hashCode() {
        return activities.hashCode();
    }
}
