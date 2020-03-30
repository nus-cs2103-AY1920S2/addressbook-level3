package seedu.zerotoone.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.Workout;

public interface WorkoutLogic {
    // -----------------------------------------------------------------------------------------
    // Workout List
    /**
     * Returns the WorkoutList.
     *
     * @see seedu.zerotoone.model.Model#getWorkoutList()
     */
    ReadOnlyWorkoutList getWorkoutList();

    /** Returns an unmodifiable view of the filtered list of workouts */
    ObservableList<Workout> getFilteredWorkoutList();

    /**
     * Returns the user prefs' workout list file path.
     */
    Path getWorkoutListFilePath();
}