//@@author yapdianhao
package seedu.jelphabot.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.testutil.TypicalReminderShowsTasks.DEFAULT_REMINDER_SHOWS_TASK;
import static seedu.jelphabot.testutil.TypicalReminders.BOOK_REPORT_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.testutil.ReminderShowsTaskBuilder;

public class ReminderShowsTaskTest {

    @Test
    public void isSameReminderShowsTask() {

        assertFalse(DEFAULT_REMINDER_SHOWS_TASK.equals(null));

        assertTrue(DEFAULT_REMINDER_SHOWS_TASK.equals(DEFAULT_REMINDER_SHOWS_TASK));

        ReminderShowsTask editedReminderShowsTask = new ReminderShowsTaskBuilder()
                                                        .withReminder(BOOK_REPORT_REMINDER)
                                                        .build();
        assertFalse(DEFAULT_REMINDER_SHOWS_TASK.equals(editedReminderShowsTask));
    }
}
