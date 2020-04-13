//@@author yapdianhao
package seedu.jelphabot.model.reminder.exceptions;

/**
 * Signals that the operation will result in duplicate Reminders
 * (Reminders are considered duplicates if they have the same
 * identity).
 */
public class DuplicateReminderShowsTaskException extends RuntimeException {
    public DuplicateReminderShowsTaskException() {
        super("Operation would result in duplicate reminders");
    }
}
