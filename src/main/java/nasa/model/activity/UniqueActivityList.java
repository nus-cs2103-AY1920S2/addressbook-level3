package nasa.model.activity;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nasa.commons.core.index.Index;
import nasa.model.activity.exceptions.ActivityNotFoundException;
import nasa.model.activity.exceptions.DuplicateActivityException;

/**
 * A list of activities that enforces uniqueness between its elements and does not allow nulls.
 * A activity is considered unique by comparing using {@code Activity#isSameActivity(Activity)}.
 * As such, adding and updating of activity uses Activity#isSameActivity(Activity)
 * for equality so as to ensure that the activity being added or updated is
 * unique in terms of identity in the UniqueActivityList. However, the removal of a activity uses
 * Activity#equals(Object) so as to ensure that the activity with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 *
 */
public abstract class UniqueActivityList<T extends Activity> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent T as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a activity to the list.
     * The activity must not already exist in the list.
     * @param toAdd Activity.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    public ObservableList<T> getActivityList() {
        return this.internalList;
    }

    public T getActivityByIndex(Index index) {
        return internalList.get(index.getZeroBased());
    }

    public T getActivityByName(Name name) {
        return internalList.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .get();
    }

    public void setActivity(T targetActivity, T editedActivity) {
        requireAllNonNull(targetActivity, editedActivity);

        int index = internalList.indexOf(targetActivity);
        if (index == -1) {
            throw new ActivityNotFoundException();
        }

        // case when editedActivity is a non-target activity that already exists in { @code UniqueActivityList }
        if (!targetActivity.equals(editedActivity) && contains(editedActivity)) {
            throw new DuplicateActivityException();
        }
        internalList.set(index, editedActivity);
    }

    /**
     * Removes the equivalent activity from the list.
     * The activity must exist in the list.
     * @param toRemove Activity.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ActivityNotFoundException();
        }
    }

    /**
     * Removes the equivalent activity from the list by index.
     * The activity must exist in the list.
     * @param index Index.
     */
    public void removeByIndex(Index index) {
        requireNonNull(index);
        internalList.remove(index.getZeroBased());
    }

    /**
     * Empty all the activity inside the list.
     */
    public void removeAll() {
        internalList.clear();
    }

    /**
     * Reset activities with the given list.
     * @param replacement UniqueActivityList
     */
    public void setActivities(UniqueActivityList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.getDeepCopyList());
    }

    /**
     * Replaces the contents of this list with {@code activities}.
     * {@code activities} must not contain duplicate activities.
     * @param activities List
     */
    public void setActivities(List<T> activities) {
        requireAllNonNull(activities);
        internalList.setAll(activities);
    }

    /**
     * Return a copy of activity list.
     * @return ObservableList
     */
    public ObservableList<Activity> getDeepCopyList() {
        ObservableList<Activity> deepCopyList = FXCollections.observableArrayList();
        for (Activity activity : internalUnmodifiableList) {
            deepCopyList.add(activity.deepCopy());
        }
        return deepCopyList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     * @return ObservableList
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public void setSchedule(Index index, Index type) {
        T item = internalList.get(index.getZeroBased());
        item.setSchedule(type.getZeroBased());
        internalList.set(index.getZeroBased(), item);
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueActivityList // instanceof handles nulls
                && internalList.equals(((UniqueActivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    public ObservableList<T> getInternalList() {
        return internalList;
    }

    public ObservableList<T> getInternalUnmodifiableList() {
        return internalUnmodifiableList;
    }
}
