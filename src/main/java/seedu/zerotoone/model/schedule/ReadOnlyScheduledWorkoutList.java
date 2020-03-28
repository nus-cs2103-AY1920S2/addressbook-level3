package seedu.zerotoone.model.schedule;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a scheduled workout list
 */
public interface ReadOnlyScheduledWorkoutList {

    /**
     * Returns an unmodifiable view of the exercises list.
     * This list will not contain any duplicate exercises.
     */
    ObservableList<ScheduledWorkout> getScheduledWorkoutList();
}
