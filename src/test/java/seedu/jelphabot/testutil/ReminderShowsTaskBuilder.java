//@@author yapdianhao
package seedu.jelphabot.testutil;

import static seedu.jelphabot.testutil.TypicalReminders.ASSESSMENT_REMINDER;
import static seedu.jelphabot.testutil.TypicalTasks.ASSESSMENT;

import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderShowsTask;
import seedu.jelphabot.model.task.Task;
/**
 * A utility class to help build ReminderShowsTask objects.
 */
public class ReminderShowsTaskBuilder {

    private Reminder reminder;
    private Task task;

    public ReminderShowsTaskBuilder() {
        reminder = ASSESSMENT_REMINDER;
        task = ASSESSMENT;
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     * @param reminderToCopy
     */
    public ReminderShowsTaskBuilder(Reminder reminderToCopy, Task taskToCopy) {
        reminder = reminderToCopy;
        task = taskToCopy;
    }

    /**
     * Sets the {@code Reminder} of the {@code ReminderShowsTask} that we are building.
     */
    public ReminderShowsTaskBuilder withReminder(Reminder reminder) {
        this.reminder = reminder;
        return this;
    }

    /**
     * Sets the {@code Task} of the {@code ReminderShowsTask} that we are building.
     */
    public ReminderShowsTaskBuilder withTask(Task task) {
        this.task = task;
        return this;
    }

    public ReminderShowsTask build() {
        return new ReminderShowsTask(reminder, task);
    }
}
