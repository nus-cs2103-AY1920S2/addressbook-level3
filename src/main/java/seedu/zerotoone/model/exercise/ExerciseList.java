package seedu.zerotoone.model.exercise;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.zerotoone.commons.core.LogsCenter;

/**
 * Wraps all data at the exercise list level
 * Duplicates are not allowed (by .isSameExercise comparison)
 */
public class ExerciseList implements ReadOnlyExerciseList {

    private final UniqueExerciseList exercises;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        exercises = new UniqueExerciseList();
    }

    public ExerciseList() {}

    /**
     * Creates an ExerciseList using the Exercises in the {@code toBeCopied}
     */
    public ExerciseList(ReadOnlyExerciseList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the exercise list with {@code exercises}.
     * {@code exercises} must not contain duplicate exercises.
     */
    public void setExercises(List<Exercise> exercises) {
        logger.fine("Replacing current list with " + exercises);
        this.exercises.setExercises(exercises);
    }

    /**
     * Resets the existing data of this {@code ExerciseList} with {@code newData}.
     */
    public void resetData(ReadOnlyExerciseList newData) {
        requireNonNull(newData);
        logger.fine("Resetting data with " + newData);
        setExercises(newData.getExerciseList());
    }

    //// exercise-level operations

    /**
     * Returns true if a exercise with the same identity as {@code exercise} exists in the exercise list.
     */
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        logger.fine("Checking if list contains " + exercise);
        return exercises.contains(exercise);
    }

    /**
     * Adds a exercise to the exercise list.
     * The exercise must not already exist in the exercise list.
     */
    public void addExercise(Exercise p) {
        logger.fine("Adding " + p + " into Exercise List");
        exercises.add(p);
    }

    /**
     * Replaces the given exercise {@code target} in the list with {@code editedExercise}.
     * {@code target} must exist in the exercise list.
     * The exercise identity of {@code editedExercise} must not be the same as another existing
     * exercise in the exercise list.
     */
    public void setExercise(Exercise target, Exercise editedExercise) {
        requireNonNull(editedExercise);
        logger.fine("Setting Exercise " + target + " with " + editedExercise);
        exercises.setExercise(target, editedExercise);
    }

    /**
     * Removes {@code key} from this {@code ExerciseList}.
     * {@code key} must exist in the exercise list.
     */
    public void removeExercise(Exercise key) {
        logger.fine("Removing Exercise " + key + " from Exercise List");
        exercises.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return exercises.asUnmodifiableObservableList().size() + " exercises";
        // TODO: refine later
    }

    @Override
    public ObservableList<Exercise> getExerciseList() {
        return exercises.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExerciseList // instanceof handles nulls
                && exercises.equals(((ExerciseList) other).exercises));
    }

    @Override
    public int hashCode() {
        return exercises.hashCode();
    }
}
