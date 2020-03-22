package seedu.zerotoone.model.workout;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an exercise list
 */
public interface ReadOnlyWorkoutList {

    /**
     * Returns an unmodifiable view of the exercises list.
     * This list will not contain any duplicate exercises.
     */
    ObservableList<Workout> getWorkoutList();

}
