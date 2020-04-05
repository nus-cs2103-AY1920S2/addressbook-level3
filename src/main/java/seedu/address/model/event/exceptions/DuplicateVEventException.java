package seedu.address.model.event.exceptions;

/**
 * Signals that the operation will result in duplicate VEvents (VEvents are considered duplicates if they
 * have the startDateTime, endDateTime and eventName).
 */
public class DuplicateVEventException extends RuntimeException {
    public DuplicateVEventException() {
        super("Operation would result in duplicate VEvents");
    }
}

