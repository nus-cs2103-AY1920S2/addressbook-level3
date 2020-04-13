package seedu.zerotoone.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.zerotoone.model.schedule.exceptions.DuplicateScheduledWorkoutException;

/**
 * A list of scheduledWorkouts that enforces uniqueness between its elements and does not allow nulls.
 * A scheduledWorkout is considered unique by comparing using
 * {@code ScheduledWorkout#isSameScheduledWorkout(ScheduledWorkout)}. As such, adding and updating of scheduledWorkouts
 * uses ScheduledWorkout#isSameScheduledWorkout(ScheduledWorkout) for equality so as to ensure that the
 * scheduledWorkout being added or updated is unique in terms of identity in the UniqueScheduledWorkoutList. However,
 * the removal of a scheduledWorkout uses ScheduledWorkout#equals(Object) so as to ensure that the scheduledWorkout
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see ScheduledWorkout#isSameScheduledWorkout(ScheduledWorkout)
 */
public class UniqueScheduledWorkoutList implements Iterable<ScheduledWorkout> {

    private final ObservableList<ScheduledWorkout> internalList = FXCollections.observableArrayList();
    private final ObservableList<ScheduledWorkout> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent scheduledWorkout as the given argument.
     */
    public boolean contains(ScheduledWorkout toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameScheduledWorkout);
    }

    /**
     * Adds a scheduledWorkout to the list.
     * The scheduledWorkout must not already exist in the list.
     */
    public void add(ScheduledWorkout toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateScheduledWorkoutException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code scheduledWorkouts}.
     * {@code scheduledWorkouts} must not contain duplicate scheduledWorkouts.
     */
    public void setScheduledWorkouts(List<ScheduledWorkout> scheduledWorkouts) {
        requireAllNonNull(scheduledWorkouts);
        if (!scheduledWorkoutsAreUnique(scheduledWorkouts)) {
            throw new DuplicateScheduledWorkoutException();
        }

        internalList.setAll(scheduledWorkouts);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ScheduledWorkout> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ScheduledWorkout> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueScheduledWorkoutList // instanceof handles nulls
                && internalList.equals(((UniqueScheduledWorkoutList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code scheduledWorkouts} contains only unique scheduledWorkouts.
     */
    private boolean scheduledWorkoutsAreUnique(List<ScheduledWorkout> scheduledWorkouts) {
        for (int i = 0; i < scheduledWorkouts.size() - 1; i++) {
            for (int j = i + 1; j < scheduledWorkouts.size(); j++) {
                if (scheduledWorkouts.get(i).isSameScheduledWorkout(scheduledWorkouts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
