//@@author yapdianhao
package seedu.jelphabot.model.util;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.ReadOnlyJelphaBot;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderDay;
import seedu.jelphabot.model.reminder.ReminderHour;

/**
 * Contains utility methods for populating {@code JelphaBot} with sample reminders data.
 */
public class SampleReminderUtil {

    public static Reminder[] getSampleReminders() {
        return new Reminder[] {
            new Reminder(Index.fromOneBased(1), new ReminderDay("1"), new ReminderHour("1")),
            new Reminder(Index.fromOneBased(2), new ReminderDay("1"), new ReminderHour("4")),
            new Reminder(Index.fromOneBased(3), new ReminderDay("2"), new ReminderHour("2"))
        };
    }

    public static ReadOnlyJelphaBot getSampleJelphaBot() {
        JelphaBot sampleAb = new JelphaBot();
        for (Reminder sampleReminder : getSampleReminders()) {
            sampleAb.addReminder(sampleReminder);
        }
        return sampleAb;
    }
}
