//@@author yapdianhao
package seedu.jelphabot.model.reminder;

import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.commons.core.index.Index;

/**
 * Represents a reminder in Jelphabot. Reminder has an index of task to be reminded of, and days and hours before
 * the task dues.
 */
public class Reminder {

    private static final Logger logger = LogsCenter.getLogger(Reminder.class);

    private final ReminderDay daysToRemind;

    private final ReminderHour hoursToRemind;

    private Index index;

    public Reminder(Index index, ReminderDay daysToRemind, ReminderHour hoursToRemind) {
        requireAllNonNull(index, daysToRemind, hoursToRemind);
        this.index = index;
        this.daysToRemind = daysToRemind;
        this.hoursToRemind = hoursToRemind;
    }

    public Index getIndex() {
        return this.index;
    }

    public ReminderDay getDaysToRemind() {
        return this.daysToRemind;
    }

    public ReminderHour getHoursToRemind() {
        return this.hoursToRemind;
    }

    public void setIndex(int index) {
        Index updatedIndex = Index.fromOneBased(index);
        this.index = updatedIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof Reminder // instanceof handles nulls
                           && index.equals(((Reminder) other).index)); // state check
    }

    /**
     * Returns true is both reminder refers to the same task.
     * @param other Reminder
     * @return boolean
     */
    public boolean isSameReminder(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getIndex().equals(getIndex());
    }

}
