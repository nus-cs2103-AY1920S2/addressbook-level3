package seedu.address.model.lesson.exceptions;

/**
 * Signals that start time and end time entered are invalid.
 */
public class InvalidTimeRangeException extends RuntimeException {
    public InvalidTimeRangeException() {
        super("Start time and end time provided are invalid. Start time should be earlier than the end time.");
    }
}
