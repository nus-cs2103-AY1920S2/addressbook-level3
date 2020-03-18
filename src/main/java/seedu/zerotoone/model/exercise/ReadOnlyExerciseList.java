package seedu.zerotoone.model.exercise;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an exercise list
 */
public interface ReadOnlyExerciseList {

    /**
     * Returns an unmodifiable view of the exercises list.
     * This list will not contain any duplicate exercises.
     */
    ObservableList<Exercise> getExerciseList();

}
