//@@author yapdianhao
package seedu.jelphabot.model.reminder;

import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;
import seedu.jelphabot.model.task.Task;

/**
 * A class to show relevant information about a task and a reminder that is linked.
 */
public class ReminderShowsTask {

    private Description description;
    private ModuleCode moduleCode;
    private DateTime dateTime;
    private ReminderDay reminderDay;
    private ReminderHour reminderHour;
    private Task task;
    private Reminder reminder;

    public ReminderShowsTask(Reminder reminder, Task task) {
        this.description = task.getDescription();
        this.moduleCode = task.getModuleCode();
        this.dateTime = task.getDateTime();
        this.reminderDay = reminder.getDaysToRemind();
        this.reminderHour = reminder.getHoursToRemind();
        this.task = task;
        this.reminder = reminder;
    }

    public Description getDescription() {
        return this.description;
    }

    public ModuleCode getModuleCode() {
        return this.moduleCode;
    }

    public DateTime getDateTime() {
        return this.dateTime;
    }

    public ReminderDay getReminderDay() {
        return this.reminderDay;
    }

    public ReminderHour getReminderHour() {
        return this.reminderHour;
    }

    public Reminder getReminder() {
        return this.reminder;
    }

    public Task getTask() {
        return this.task;
    }
    /**
     * Check if another object is equal to this ReminderShowsTask object.
     * @param other
     * @return boolean
     */
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof ReminderShowsTask // instanceof handles nulls
                           && reminder.equals(((ReminderShowsTask) other).reminder)
                           && task.equals(((ReminderShowsTask) other).task)); // state check
    }
}
