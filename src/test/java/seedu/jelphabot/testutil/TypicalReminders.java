//@@author yapdianhao
package seedu.jelphabot.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.reminder.Reminder;

/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {

    public static final Reminder ASSESSMENT_REMINDER =
        new ReminderBuilder().withIndex("0")
            .withReminderDay("1")
            .withReminderHour("1").build();

    public static final Reminder BOOK_REPORT_REMINDER =
        new ReminderBuilder().withIndex("1")
            .withReminderDay("2")
            .withReminderHour("2").build();

    public static final Reminder ASSIGNMENT_REMINDER =
        new ReminderBuilder().withIndex("2").withReminderDay("3").withReminderHour("3").build();

    private TypicalReminders() {
    }

    /**
     * Returns an {@code JelphaBot} with all the typical tasks.
     */
    public static JelphaBot getTypicalJelphaBot() {
        JelphaBot ab = new JelphaBot();
        for (Reminder reminder: getTypicalReminders()) {
            ab.addReminder(reminder);
        }
        return ab;
    }

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(ASSESSMENT_REMINDER));
    }

}
