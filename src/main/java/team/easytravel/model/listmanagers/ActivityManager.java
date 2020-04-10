package team.easytravel.model.listmanagers;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import team.easytravel.commons.core.time.Date;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.util.uniquelist.UniqueList;

/**
 * Wraps all data at the ActivityManager level
 * Duplicates are not allowed (by.equals comparison)
 */
public class ActivityManager implements ReadOnlyActivityManager {
    private final UniqueList<Activity> activities;

    /**
     * Instantiates a new ActivityManager.
     */
    public ActivityManager() {
        activities = new UniqueList<>();
    }

    /**
     * Creates an ActivityManager using the Activities in the {@code} toBeCopied}
     */
    public ActivityManager(ReadOnlyActivityManager toBeCopied) {
        activities = new UniqueList<>();
        resetData(toBeCopied);
    }

    // List overwrite operations

    /**
     * Replaces the contents of the activity list with {@code activities}.
     * {@code activities} must not contain duplicate activities.
     */
    public void setActivities(List<Activity> activities) {
        this.activities.setElements(activities);
    }

    /**
     * Resets the existing data of this {@code ActivityManager} with {@code newData}
     */
    public void resetData(ReadOnlyActivityManager newData) {
        requireNonNull(newData);
        setActivities(newData.getActivityList());
    }
    /**
     * Sorts the fixed expense list with the specified comparator.
     */
    public void sortActivityList(Comparator<Activity> cmp) {
        activities.sort(cmp);
    }

    /**
     * Clears all invalid scheduled time for every activity.
     * A scheduled time is invalid when it starts earlier than the given date.
     */
    public void clearInvalidScheduleTime(Date tripStartDate) {
        setActivities(getActivityList().stream()
                .map(activity -> {
                    if (activity.getScheduledDateTime().isPresent()
                            && activity.getScheduledDateTime().get().getDate().compareTo(tripStartDate) < 0) {
                        return new Activity(activity.getTitle(), activity.getDuration(), activity.getLocation(),
                                activity.getTags(), Optional.empty());
                    } else {
                        return activity;
                    }
                }).collect(Collectors.toList()));
    }

    // Activity-level operations

    /**
     * Returns true if an activity with the same identity as {@code activity} exists in the ActivityManager.
     */
    public boolean hasActivity(Activity activity) {
        requireNonNull(activity);
        return activities.contains(activity);
    }

    /**
     * Adds an activity to the ActivityManager.
     * The activity must not already exist in the ActivityManager.
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * Replaces the given activity {@code target} in the list with {@code editedActivity}.
     * {@code target} must exist in the ActivityManager.
     * The activity identity of {@code editedActivity} must not be the same as another existing activity in the
     * ActivityManager.
     */
    public void setActivity(Activity target, Activity editedActivity) {
        requireNonNull(editedActivity);

        activities.setElement(target, editedActivity);
    }

    /**
     * Removes {@code key} from this {@code ActivityManager}.
     * {@code key} must exist in the ActivityManager.
     */
    public void removeActivity(Activity key) {
        activities.remove(key);
    }

    // Util methods

    @Override
    public String toString() {
        return activities.asUnmodifiableObservableList().size() + "  activities";
        // TODO: refine later
    }

    public ObservableList<Activity> getActivityList() {
        return activities.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityManager // instanceof handles nulls
                && activities.equals(((ActivityManager) other).activities));
    }

    @Override
    public int hashCode() {
        return activities.hashCode();
    }


}
