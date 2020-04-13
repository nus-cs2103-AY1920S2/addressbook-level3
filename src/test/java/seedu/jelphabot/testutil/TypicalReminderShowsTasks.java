//@@author yapdianhao
package seedu.jelphabot.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jelphabot.model.reminder.ReminderShowsTask;

/**
 * A utility class containing a list of {@code ReminderShowsTasks} objects to be used in tests.
 */
public class TypicalReminderShowsTasks {

    public static final ReminderShowsTask DEFAULT_REMINDER_SHOWS_TASK = new ReminderShowsTaskBuilder().build();

    private TypicalReminderShowsTasks() {
    }

    public static List<ReminderShowsTask> getTypicalReminderShowTasks() {
        return new ArrayList<>(Arrays.asList(DEFAULT_REMINDER_SHOWS_TASK));
    }
}
