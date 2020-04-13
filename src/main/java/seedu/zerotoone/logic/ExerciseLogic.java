package seedu.zerotoone.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;

/**
 * Logic for Exercise.
 */
public interface ExerciseLogic {
    /**
     * Returns the ExerciseList.
     *
     * @see seedu.zerotoone.model.Model#getExerciseList()
     */
    ReadOnlyExerciseList getExerciseList();

    /** Returns an unmodifiable view of the filtered list of exercises */
    ObservableList<Exercise> getFilteredExerciseList();

    /**
     * Returns the user prefs' exercise list file path.
     */
    Path getExerciseListFilePath();
}
