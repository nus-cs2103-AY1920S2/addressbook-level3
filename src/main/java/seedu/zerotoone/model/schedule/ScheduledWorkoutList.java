package seedu.zerotoone.model.schedule;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the scheduled workout list level
 * Duplicates are not allowed (by .isSameScheduledWorkout comparison)
 */
public class ScheduledWorkoutList implements ReadOnlyScheduledWorkoutList {

    private final UniqueScheduledWorkoutList scheduledWorkouts;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        scheduledWorkouts = new UniqueScheduledWorkoutList();
    }

    public ScheduledWorkoutList() {}

    public ScheduledWorkoutList(ReadOnlyScheduledWorkoutList toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    //// list overwrite operations

    /**
     * Replaces the contents of the scheduledWorkout list with {@code scheduledWorkouts}.
     * {@code scheduledWorkouts} must not contain duplicate scheduledWorkouts.
     */
    public void setScheduledWorkouts(List<ScheduledWorkout> scheduledWorkouts) {
        this.scheduledWorkouts.setScheduledWorkouts(scheduledWorkouts);
    }

    /**
     * Resets the existing data of this {@code ScheduledWorkoutList} with {@code newData}.
     */
    public void resetData(ReadOnlyScheduledWorkoutList newData) {
        requireNonNull(newData);

        setScheduledWorkouts(newData.getScheduledWorkoutList());
    }

    //// scheduledWorkout-level operations

    /**
     * Returns true if a scheduledWorkout with the same identity as {@code scheduledWorkout} exists in the
     * scheduledWorkout list.
     */
    public boolean hasScheduledWorkout(ScheduledWorkout scheduledWorkout) {
        requireNonNull(scheduledWorkout);
        return scheduledWorkouts.contains(scheduledWorkout);
    }

    /**
     * Adds a scheduledWorkout to the scheduledWorkout list.
     * The scheduledWorkout must not already exist in the scheduledWorkout list.
     */
    public void addScheduledWorkout(ScheduledWorkout p) {
        scheduledWorkouts.add(p);
    }

    // /**
    //  * Replaces the given scheduledWorkout {@code target} in the list with {@code editedScheduledWorkout}.
    //  * {@code target} must exist in the scheduledWorkout list.
    //  * The scheduledWorkout identity of {@code editedScheduledWorkout} must not be the same as another existing
    //  * scheduledWorkout in the scheduledWorkout list.
    //  */
    // public void setScheduledWorkout(ScheduledWorkout target, ScheduledWorkout editedScheduledWorkout) {
    //     requireNonNull(editedScheduledWorkout);
    //
    //     scheduledWorkouts.setScheduledWorkout(target, editedScheduledWorkout);
    // }

    // /**
    //  * Removes {@code key} from this {@code ScheduledWorkoutList}.
    //  * {@code key} must exist in the scheduledWorkout list.
    //  */
    // public void removeScheduledWorkout(ScheduledWorkout key) {
    //     scheduledWorkouts.remove(key);
    // }

    //// util methods

    @Override
    public String toString() {
        return scheduledWorkouts.asUnmodifiableObservableList().size() + " scheduled workouts";
        // TODO: refine later
    }

    @Override
    public ObservableList<ScheduledWorkout> getScheduledWorkoutList() {
        return scheduledWorkouts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduledWorkoutList // instanceof handles nulls
                && scheduledWorkouts.equals(((ScheduledWorkoutList) other).scheduledWorkouts));
    }

    @Override
    public int hashCode() {
        return scheduledWorkouts.hashCode();
    }
}
