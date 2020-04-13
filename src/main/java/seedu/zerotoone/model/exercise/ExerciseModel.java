package seedu.zerotoone.model.exercise;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

/**
 * Model for Exercise.
 */
public interface ExerciseModel {
    /**
     * Returns the user prefs' exercise list file path.
     */
    Path getExerciseListFilePath();

    /**
     * Sets the user prefs' exercise list file path.
     */
    void setExerciseListFilePath(Path exerciseListFilePath);

    /**
     * Replaces exercise list data with the data in {@code exerciseList}.
     */
    void setExerciseList(ReadOnlyExerciseList exerciseList);

    /** Returns the ExerciseList */
    ReadOnlyExerciseList getExerciseList();

    /**
     * Returns true if a exercise with the same identity as {@code exercise} exists in the exercise list.
     */
    boolean hasExercise(Exercise exercise);

    /**
     * Deletes the given exercise.
     * The exercise must exist in the exercise list.
     */
    void deleteExercise(Exercise target);

    /**
     * Adds the given exercise.
     * {@code exercise} must not already exist in the exercise list.
     */
    void addExercise(Exercise exercise);

    /**
     * Replaces the given exercise {@code target} with {@code editedExercise}.
     * {@code target} must exist in the exercise list.
     * The exercise identity of {@code editedExercise} must not be the same as another
     * existing exercise in the exercise list.
     */
    void setExercise(Exercise target, Exercise editedExercise);

    /**
     * Returns an unmodifiable view of the list of {@code Exercise} backed by the internal list of
     * {@code versionedExerciseList}
     */
    ObservableList<Exercise> getFilteredExerciseList();

    /**
     * Updates the filter of the filtered exercise list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExerciseList(Predicate<Exercise> predicate);
}
