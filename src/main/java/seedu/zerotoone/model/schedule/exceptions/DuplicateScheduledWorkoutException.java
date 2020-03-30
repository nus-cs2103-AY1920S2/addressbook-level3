package seedu.zerotoone.model.schedule.exceptions;

/**
 * Signals that the operation will result in duplicate ScheduledWorkouts (ScheduledWorkouts are considered duplicates
 * if they have the same identity).
 */
public class DuplicateScheduledWorkoutException extends RuntimeException {
    public DuplicateScheduledWorkoutException() {
        super("Operation would result in duplicate scheduledWorkouts");
    }
}
