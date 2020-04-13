package seedu.jelphabot.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderShowsTask;
import seedu.jelphabot.model.reminder.UniqueReminderList;
import seedu.jelphabot.model.reminder.UniqueReminderShowsTaskList;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.UniqueTaskList;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameTask comparison)
 */
public class JelphaBot implements ReadOnlyJelphaBot {

    private final List<Task> taskList;
    private final List<Reminder> reminderList;
    private final UniqueTaskList tasks;
    private final UniqueReminderList reminders;
    private final UniqueReminderShowsTaskList reminderShowsTaskList = new UniqueReminderShowsTaskList();

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        taskList = new ArrayList<>();
        reminderList = new ArrayList<>();
        tasks = new UniqueTaskList();
        reminders = new UniqueReminderList();
    }

    public JelphaBot() {}

    /**
     * Creates an JelphaBot using the Tasks in the {@code toBeCopied}
     */
    public JelphaBot(ReadOnlyJelphaBot toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders.setReminders(reminders);
    }

    public void setReminderShowsTasks(List<ReminderShowsTask> reminderShowsTasks) {
        this.reminderShowsTaskList.setReminderShowsTasks(reminderShowsTasks);
    }

    /**
     * deletes a reminder will cause all reminders' indexes after to shift forward.
     */
    public void updateDeletedReminder(Index deletedIndex) {
        int index = deletedIndex.getOneBased();
        reminders.updateReminderIndexes(index);
    }

    /**
     * Resets the existing data of this {@code JelphaBot} with {@code newData}.
     */
    public void resetData(ReadOnlyJelphaBot newData) {
        requireNonNull(newData);
        for (Task task : newData.getTaskList()) {
            taskList.add(task);
        }
        for (Reminder reminder : newData.getReminderList()) {
            reminderList.add(reminder);
        }
        setReminders(newData.getReminderList());
        setTasks(newData.getTaskList());
        setReminderShowsTasks(newData.getReminderShowsTaskList());
    }

    //// task-level operations

    /**
     * Returns true if a task with the same identity as {@code task} exists in the JelphaBot.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in JelphaBot.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Returns true if a task is currently being timed.
     */
    public boolean hasTaskBeingTimed() {
        return tasks.hasTaskBeingTimed();
    }

    /**
     * Adds a task to the task list.
     * The task must not already exist in the task list.
     */
    public void addTask(Task p) {
        tasks.add(p);
    }

    public void addReminder(Reminder r) {
        reminders.add(r);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the task list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task list.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    /**
     * Removes {@code key} from this {@code JelphaBot}.
     * {@code key} must exist in the task list.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    public void removeReminder(Reminder key) {
        reminders.remove(key);
    }
    //// util methods

    /**
     * adds the ReminderShowsTask Object to the panel.
     */
    public void addReminderShowsTask(Reminder reminder, ViewTaskList lastShownList) {
        Task task = lastShownList.get(reminder.getIndex().getZeroBased());
        ReminderShowsTask reminderShowsTask = new ReminderShowsTask(reminder, task);
        reminderShowsTaskList.add(reminderShowsTask);
    }

    /**
     * deletes the ReminderShowsTask Object from the panel list.
     */
    public void deleteReminderShowsTask(Reminder reminder, ViewTaskList lastShownList) {
        Task task = lastShownList.get(reminder.getIndex().getZeroBased());
        ReminderShowsTask reminderShowsTask = new ReminderShowsTask(reminder, task);
        reminderShowsTaskList.remove(reminderShowsTask);
    }

    @Override
    public String toString() {
        return tasks.asUnmodifiableObservableList().size() + " tasks";
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return reminders.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<ReminderShowsTask> getReminderShowsTaskList() {
        return reminderShowsTaskList.asUnmodifiableObservableList();
    }

    public List<Task> getTasksAsList() {
        return taskList;
    }

    public List<Reminder> getRemindersAsList() {
        return reminderList;
    }

    /**
     * Updates the reminder list panel after adding / delete a reminder.
     */
    public void updateReminderShowsTask(ViewTaskList viewTaskList) {
        for (Reminder reminder : getReminderList()) {
            int idx = reminder.getIndex().getZeroBased();
            Task currTask = viewTaskList.get(idx);
            ReminderShowsTask reminderShowsTask = new ReminderShowsTask(reminder, currTask);
            reminderShowsTaskList.add(reminderShowsTask);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JelphaBot // instanceof handles nulls
                && tasks.equals(((JelphaBot) other).tasks));
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }
}
