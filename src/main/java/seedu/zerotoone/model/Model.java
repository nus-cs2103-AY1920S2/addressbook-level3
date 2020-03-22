package seedu.zerotoone.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.zerotoone.commons.core.GuiSettings;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.userprefs.ReadOnlyUserPrefs;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.Workout;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Exercise> PREDICATE_SHOW_ALL_EXERCISES = unused -> true;

    // -----------------------------------------------------------------------------------------
    // Common - User Preferences
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    // -----------------------------------------------------------------------------------------
    // Exercise List
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

     // -----------------------------------------------------------------------------------------
    // Workout List
    /**
     * Returns the user prefs' workout list file path.
     */
    Path getWorkoutListFilePath();

    /**
     * Sets the user prefs' workout list file path.
     */
    void setWorkoutListFilePath(Path workoutListFilePath);

    /**
     * Replaces workout list data with the data in {@code workoutList}.
     */
    void setWorkoutList(ReadOnlyWorkoutList workoutList);

    /** Returns the WorkoutList */
    ReadOnlyWorkoutList getWorkoutList();

    /**
     * Returns true if a exercise with the same identity as {@code workout} exists in the workout list.
     */
    boolean hasWorkout(Workout workout);

    /**
     * Deletes the given workout.
     * The workout must exist in the workout list.
     */
    void deleteWorkout(Workout target);

    /**
     * Adds the given workout.
     * {@code workout} must not already exist in the workout list.
     */
    void addWorkout(Workout workout);

    /**
     * Replaces the given workout {@code target} with {@code editedWorkout}.
     * {@code target} must exist in the workout list.
     * The workout identity of {@code editedWorkout} must not be the same as another
     * existing workout in the workout list.
     */
    void setWorkout(Workout target, Workout editedExercise);

    /**
     * Returns an unmodifiable view of the list of {@code Workout} backed by the internal list of
     * {@code versionedWorkoutList}
     */
    ObservableList<Workout> getFilteredWorkoutList();

    /**
     * Updates the filter of the filtered workout list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWorkoutList(Predicate<Workout> predicate);
}
