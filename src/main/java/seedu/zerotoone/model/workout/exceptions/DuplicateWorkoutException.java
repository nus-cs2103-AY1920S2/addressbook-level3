package seedu.zerotoone.model.workout.exceptions;

/**
 * Signals that the operation will result in duplicate Exercises (Exercises are considered duplicates if they have the
 * same identity).
 */
public class DuplicateWorkoutException extends RuntimeException {
    public DuplicateWorkoutException() {
        super("Operation would result in duplicate workouts");
    }
}
