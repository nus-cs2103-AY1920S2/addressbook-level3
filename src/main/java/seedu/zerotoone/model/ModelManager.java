package seedu.zerotoone.model;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.zerotoone.commons.core.GuiSettings;
import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.userprefs.ReadOnlyUserPrefs;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutList;

/**
 * Represents the in-memory model of the exercise list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final ExerciseList exerciseList;
    private final FilteredList<Exercise> filteredExercises;
    private final WorkoutList workoutList;
    private final FilteredList<Workout> filteredWorkouts;

    /**
     * Initializes a ModelManager with the given exerciseList and userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs, ReadOnlyExerciseList exerciseList, ReadOnlyWorkoutList workoutList) {
        super();
        requireAllNonNull(exerciseList, userPrefs);
        logger.fine("Initializing with user prefs " + userPrefs);

        this.exerciseList = new ExerciseList(exerciseList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExercises = new FilteredList<>(this.exerciseList.getExerciseList());
        this.workoutList = new WorkoutList(workoutList);
        filteredWorkouts = new FilteredList<>(this.workoutList.getWorkoutList());
    }

    public ModelManager() {
        this(new UserPrefs(), new ExerciseList(), new WorkoutList());
    }

    // -----------------------------------------------------------------------------------------
    // Common - User Preferences
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    // -----------------------------------------------------------------------------------------
    // Exercise List
    @Override
    public Path getExerciseListFilePath() {
        return userPrefs.getExerciseListFilePath();
    }

    @Override
    public void setExerciseListFilePath(Path exerciseListFilePath) {
        requireNonNull(exerciseListFilePath);
        userPrefs.setExerciseListFilePath(exerciseListFilePath);
    }

    @Override
    public void setExerciseList(ReadOnlyExerciseList exerciseList) {
        this.exerciseList.resetData(exerciseList);
    }

    @Override
    public ReadOnlyExerciseList getExerciseList() {
        return exerciseList;
    }

    @Override
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        return exerciseList.hasExercise(exercise);
    }

    @Override
    public void deleteExercise(Exercise target) {
        exerciseList.removeExercise(target);
    }

    @Override
    public void addExercise(Exercise exercise) {
        exerciseList.addExercise(exercise);
        updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
    }

    @Override
    public void setExercise(Exercise target, Exercise editedExercise) {
        requireAllNonNull(target, editedExercise);
        exerciseList.setExercise(target, editedExercise);
    }

    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return filteredExercises;
    }

    @Override
    public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
        requireNonNull(predicate);
        filteredExercises.setPredicate(predicate);
    }

    // -----------------------------------------------------------------------------------------
    // Workout List
    @Override
    public Path getWorkoutListFilePath() {
        return userPrefs.getWorkoutListFilePath();
    }

    @Override
    public void setWorkoutListFilePath(Path workoutListFilePath) {
        requireNonNull(workoutListFilePath);
        userPrefs.setWorkoutListFilePath(workoutListFilePath);
    }

    @Override
    public void setWorkoutList(ReadOnlyWorkoutList workoutList) {
        this.workoutList.resetData(workoutList);
    }

    @Override
    public ReadOnlyWorkoutList getWorkoutList() {
        return workoutList;
    }

    @Override
    public boolean hasWorkout(Workout workout) {
        requireNonNull(workout);
        return workoutList.hasWorkout(workout);
    }

    @Override
    public void deleteWorkout(Workout target) {
        workoutList.removeWorkout(target);
    }

    @Override
    public void addWorkout(Workout workout) {
        workoutList.addWorkout(workout);
        updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);
    }

    @Override
    public void setWorkout(Workout target, Workout editedWorkout) {
        requireAllNonNull(target, editedWorkout);
        workoutList.setWorkout(target, editedWorkout);
    }

    @Override
    public ObservableList<Workout> getFilteredWorkoutList() {
        return filteredWorkouts;
    }

    @Override
    public void updateFilteredWorkoutList(Predicate<Workout> predicate) {
        requireNonNull(predicate);
        filteredWorkouts.setPredicate(predicate);
    }

    // -----------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return exerciseList.equals(other.exerciseList)
                && userPrefs.equals(other.userPrefs)
                && filteredExercises.equals(other.filteredExercises);
    }
}
