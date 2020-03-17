package seedu.zerotoone.model;

import javafx.collections.ObservableList;
import seedu.zerotoone.model.exercise.Exercise;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyExerciseList {

    /**
     * Returns an unmodifiable view of the exercises list.
     * This list will not contain any duplicate exercises.
     */
    ObservableList<Exercise> getExerciseList();

}
