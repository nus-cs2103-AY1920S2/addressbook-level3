package seedu.jelphabot.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.jelphabot.commons.core.GuiSettings;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.model.productivity.Productivity;
import seedu.jelphabot.model.productivity.ProductivityList;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderShowsTask;
import seedu.jelphabot.model.summary.Summary;
import seedu.jelphabot.model.summary.SummaryList;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.tasklist.GroupedTaskList;
import seedu.jelphabot.model.task.tasklist.PinnedTaskList;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;

/**
 * The API of the Model component.
 */

public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Returns the GUI settings for a popup window
     */
    GuiSettings getPopUpWindowGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' task list file path.
     */
    Path getJelphaBotFilePath();

    /**
     * Sets the user prefs' task list file path.
     */
    void setJelphaBotFilePath(Path jelphaBotFilePath);

    Path getRemindersFilePath();

    /**
     * Returns the JelphaBot
     */
    ReadOnlyJelphaBot getJelphaBot();

    /**
     * Replaces jelphaBot data with the data in {@code jelphaBot}.
     */
    void setJelphaBot(ReadOnlyJelphaBot jelphaBot);

    /**
     * Returns true if a task with the same identity as {@code task} exists in the task list.
     */
    boolean hasTask(Task task);

    boolean hasReminder(Reminder reminder);

    /**
     * Deletes the given task.
     * The task must exist in the task list.
     */
    void deleteTask(Task target);

    /**
     * Deletes the given reminder.
     *
     * @param reminder
     */
    void deleteReminder(Reminder reminder);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the task list.
     */
    void addTask(Task task);

    void addReminder(Reminder reminder);

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the task list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the task list.
     */
    void setTask(Task target, Task editedTask);

    /**
     * Replaces the existing productivity with {@code productivity}.
     */
    void setProductivity(Productivity productivity);

    /**
     * Replaces the existing summary with {@code summary}.
     */
    void setSummary(Summary summary);

    void updateDeletedReminders(Index deletedIndex);

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Task> getFilteredTaskList();

    GroupedTaskList getGroupedTaskList(GroupedTaskList.Category category);

    PinnedTaskList getPinnedTaskList();

    ViewTaskList getLastShownList();

    ObservableList<Reminder> getFilteredReminderList();

    ObservableList<ReminderShowsTask> getReminderShowsTaskList();

    /**
     * Returns an unmodifiable view of the filtered task list in the Calendar
     */
    ObservableList<Task> getFilteredCalendarTaskList();

    List<Task> getTaskListFromJelphaBot();

    List<Reminder> getReminderListFromJelphaBot();

    /**
     * Returns an unmodifiable view of the incomplete tasks that are due soon,
     * as specified by the user
     * @return
     */
    //public ObservableList<Task> getFilteredByReminder();

    /**
     * Returns an unmodifiable view of the user's productivity.
     */
    ProductivityList getProductivityList();

    /**
     * Returns an unmodifiable view of the user's summary.
     *
     * @return
     */
    SummaryList getSummaryList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Updates the filter of the filtered calendar task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCalendarTaskList(Predicate<Task> predicate);

    void updateReminderShowsTask();
}
