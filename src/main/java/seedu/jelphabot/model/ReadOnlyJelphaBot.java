package seedu.jelphabot.model;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderShowsTask;
import seedu.jelphabot.model.task.Task;

/**
 * Unmodifiable view of an task list
 */
public interface ReadOnlyJelphaBot {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

    ObservableList<Reminder> getReminderList();

    ObservableList<ReminderShowsTask> getReminderShowsTaskList();

    void setTasks(List<Task> tasks);

    void setReminders(List<Reminder> reminders);

}
