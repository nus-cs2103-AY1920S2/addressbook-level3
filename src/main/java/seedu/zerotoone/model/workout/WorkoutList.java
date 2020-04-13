package seedu.zerotoone.model.workout;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.model.exercise.Exercise;

/**
 * Wraps all data at the exercise list level
 * Duplicates are not allowed (by .isSameWorkout comparison)
 */
public class WorkoutList implements ReadOnlyWorkoutList {

    private final UniqueWorkoutList workouts;

    private final Logger logger = LogsCenter.getLogger(getClass());

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        workouts = new UniqueWorkoutList();
    }

    public WorkoutList() {}

    /**
     * Creates an WorkoutList using the Workouts in the {@code toBeCopied}
     */
    public WorkoutList(ReadOnlyWorkoutList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the exercise list with {@code workouts}.
     * {@code workouts} must not contain duplicate workouts.
     */
    public void setWorkouts(List<Workout> workouts) {
        logger.info("Setting workouts");
        this.workouts.setWorkouts(workouts);
    }

    /**
     * Resets the existing data of this {@code WorkoutList} with {@code newData}.
     */
    public void resetData(ReadOnlyWorkoutList newData) {
        logger.info("Resetting workout list data");
        requireNonNull(newData);

        setWorkouts(newData.getWorkoutList());
    }

    //// workout-level operations

    /**
     * Returns true if a workout with the same identity as {@code workout} exists in the workout list.
     */
    public boolean hasWorkout(Workout exercise) {
        requireNonNull(exercise);
        return workouts.contains(exercise);
    }

    /**
     * Adds a workout to the workout list.
     * The workout must not already exist in the workout list.
     */
    public void addWorkout(Workout p) {
        logger.info("Adding workout");
        workouts.add(p);
    }

    /**
     * Replaces the given workout {@code target} in the list with {@code editedWorkout}.
     * {@code target} must exist in the workout list.
     * The workout identity of {@code editedWorkout} must not be the same as another existing
     * workout in the workout list.
     */
    public void setWorkout(Workout target, Workout editedWorkout) {
        logger.info("Setting workout");
        requireNonNull(editedWorkout);

        workouts.setWorkout(target, editedWorkout);
    }

    /**
     * Replaces the given exercise {@code target} in every workout
     * with {@code editedExercise}.
     */
    public void setExerciseInWorkouts(Exercise target, Exercise editedExercise) {
        logger.info("Setting exercise in workouts");

        requireNonNull(target);
        requireNonNull(editedExercise);

        for (Workout workout : workouts.asUnmodifiableObservableList()) {
            if (workout.hasExercise(target)) {
                System.out.println("Editing: " + target);
                workout.setExercise(target, editedExercise);
            }
        }
    }

    /**
     * Removes {@code key} from this {@code WorkoutList}.
     * {@code key} must exist in the exercise list.
     */
    public void removeWorkout(Workout key) {
        logger.info("Removing workout");
        workouts.remove(key);
    }

    /**
     * Removes {@code exercise} from all {@code Workout} in {@code WorkoutList}.
     */
    public void removeExerciseFromWorkouts(Exercise exercise) {
        logger.info("Removing exercise from workouts");

        for (Workout workout : workouts.asUnmodifiableObservableList()) {
            if (workout.hasExercise(exercise)) {
                workout.deleteExercise(exercise);
            }
        }
    }

    //// util methods
    @Override
    public String toString() {
        return workouts.asUnmodifiableObservableList().size() + " workouts";
    }

    @Override
    public ObservableList<Workout> getWorkoutList() {
        logger.info("Getting observable list of workout");

        return workouts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkoutList // instanceof handles nulls
                && workouts.equals(((WorkoutList) other).workouts));
    }

    @Override
    public int hashCode() {
        return workouts.hashCode();
    }
}
