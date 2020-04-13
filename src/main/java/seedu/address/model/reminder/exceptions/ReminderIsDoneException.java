package seedu.address.model.reminder.exceptions;

/**
 * Signals that the target reminder is already done.
 */

public class ReminderIsDoneException extends RuntimeException {
    public ReminderIsDoneException() {
        super("Reminder is already done!");
    }
}
