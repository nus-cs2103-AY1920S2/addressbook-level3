package seedu.zerotoone.model.schedule.exceptions;

/**
 * Signals that the operation will result in duplicate Schedules (Schedules are considered duplicates if they have the
 * same identity).
 */
public class DuplicateScheduleException extends RuntimeException {
    public DuplicateScheduleException() {
        super("Operation would result in duplicate schedules");
    }
}
