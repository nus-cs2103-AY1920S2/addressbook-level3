package seedu.address.model.reminder;

import java.util.List;

/**
 * Unmodifiable view of all Reminders.
 */
public interface ReadOnlyReminder {
    /**
     * Returns an unmodifiable view of the reminder list.
     * This list will not contain any duplicate reminders.
     */
    List<Reminder> getAllReminders();
}
