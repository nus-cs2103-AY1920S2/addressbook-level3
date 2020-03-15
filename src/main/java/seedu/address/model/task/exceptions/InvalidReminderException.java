package seedu.address.model.task.exceptions;

/**
 * Signals that the reminder's time is before the current time
 */
public class InvalidReminderException extends RuntimeException {
    public InvalidReminderException() {
        super("Operation would result in invalid reminder due to the wrong timing");
    }
}
