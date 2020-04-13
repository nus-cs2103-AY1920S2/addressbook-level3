package seedu.jelphabot.model;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.jelphabot.commons.core.GuiSettings;
import seedu.jelphabot.commons.core.LogsCenter;
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
 * Represents the in-memory model of the task list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final JelphaBot readOnlyJelphaBot;
    private final UserPrefs userPrefs;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Reminder> filteredReminders;
    private final FilteredList<Task> filteredCalendarTasks;
    private final ProductivityList productivityList;
    private final SummaryList summaryList;

    private GroupedTaskList lastShownList;

    /**
     * Initializes a ModelManager with the given readOnlyJelphaBot and userPrefs.
     */
    public ModelManager(ReadOnlyJelphaBot readOnlyJelphaBot, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(readOnlyJelphaBot, userPrefs);

        logger.fine("Initializing with task list: " + readOnlyJelphaBot + " and user prefs " + userPrefs);

        this.readOnlyJelphaBot = new JelphaBot(readOnlyJelphaBot);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTasks = new FilteredList<>(this.readOnlyJelphaBot.getTaskList());
        filteredReminders = new FilteredList<>(this.readOnlyJelphaBot.getReminderList());
        filteredCalendarTasks = new FilteredList<>(this.readOnlyJelphaBot.getTaskList());
        productivityList = new ProductivityList();
        summaryList = new SummaryList();
        lastShownList = getGroupedTaskList(GroupedTaskList.Category.DATE);
    }

    public ModelManager() {
        this(new JelphaBot(), new UserPrefs());
    }

    // =========== UserPrefs
    // ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public GuiSettings getPopUpWindowGuiSettings() {
        return userPrefs.getPopUpWindowGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getJelphaBotFilePath() {
        return userPrefs.getJelphaBotFilePath();
    }

    @Override
    public Path getRemindersFilePath() {
        return userPrefs.getRemindersFilePath();
    }

    @Override
    public void setJelphaBotFilePath(Path readOnlyJelphaBotFilePath) {
        requireNonNull(readOnlyJelphaBotFilePath);
        userPrefs.setJelphaBotFilePath(readOnlyJelphaBotFilePath);
    }

    public void setJelphaBotReminderFilePath(Path reminderFilePath) {
        requireAllNonNull(reminderFilePath);
        userPrefs.setJelphaBotReminderFilePath(reminderFilePath);
    }

    // =========== JelphaBot ==================================================

    @Override
    public ReadOnlyJelphaBot getJelphaBot() {
        return readOnlyJelphaBot;
    }

    @Override
    public void setJelphaBot(ReadOnlyJelphaBot readOnlyJelphaBot) {
        this.readOnlyJelphaBot.resetData(readOnlyJelphaBot);
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return readOnlyJelphaBot.hasTask(task);
    }

    @Override
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return readOnlyJelphaBot.hasReminder(reminder);
    }

    @Override
    public void deleteTask(Task target) {
        readOnlyJelphaBot.removeTask(target);
    }

    @Override
    public void deleteReminder(Reminder reminder) {
        readOnlyJelphaBot.deleteReminderShowsTask(reminder, getLastShownList());
        readOnlyJelphaBot.removeReminder(reminder);
        //updateReminderShowsTask();
    }

    @Override
    public void addTask(Task task) {
        readOnlyJelphaBot.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void addReminder(Reminder reminder) {
        readOnlyJelphaBot.addReminderShowsTask(reminder, getLastShownList());
        readOnlyJelphaBot.addReminder(reminder);
        //updateReminderShowsTask();
    }

    public void updateDeletedReminders(Index deletedIndex) {
        readOnlyJelphaBot.updateDeletedReminder(deletedIndex);
    }

    public void updateReminderShowsTask() {
        readOnlyJelphaBot.updateReminderShowsTask(getLastShownList());
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        readOnlyJelphaBot.setTask(target, editedTask);
    }

    // =========== Productivity List

    @Override
    public void setProductivity(Productivity productivity) {
        requireAllNonNull(productivity);
        productivityList.setProductivity(productivity);
    }

    @Override
    public ProductivityList getProductivityList() {
        return productivityList;
    }

    // =========== Summary List
    @Override
    public void setSummary(Summary summary) {
        requireAllNonNull(summary);
        summaryList.setSummary(summary);
    }

    @Override
    public SummaryList getSummaryList() {
        return summaryList;
    }

    // =========== Filtered Task List Accessors
    // =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the
     * internal list of {@code versionedJelphaBot}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public GroupedTaskList getGroupedTaskList(GroupedTaskList.Category category) {
        if (lastShownList == null || lastShownList.getCategory() != category) {
            lastShownList = GroupedTaskList.makeGroupedTaskList(
                getFilteredTaskList(),
                category,
                getPinnedTaskList()
            );
        }
        return lastShownList;
    }

    @Override
    public PinnedTaskList getPinnedTaskList() {
        return new PinnedTaskList(filteredTasks.filtered(task -> false), Bindings.createIntegerBinding(() -> 0));
    }

    @Override
    public ViewTaskList getLastShownList() {
        return lastShownList;
    }

    public ObservableList<Reminder> getFilteredReminderList() {
        return filteredReminders;
    }

    @Override
    public ObservableList<ReminderShowsTask> getReminderShowsTaskList() {
        return this.readOnlyJelphaBot.getReminderShowsTaskList();
    }

    @Override
    public ObservableList<Task> getFilteredCalendarTaskList() {
        return filteredCalendarTasks;
    }

    @Override
    public List<Task> getTaskListFromJelphaBot() {
        return this.readOnlyJelphaBot.getTasksAsList();
    }

    @Override
    public List<Reminder> getReminderListFromJelphaBot() {
        return this.readOnlyJelphaBot.getRemindersAsList();
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    /**
     * Updates the filter of the filtered calendar task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredCalendarTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredCalendarTasks.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return readOnlyJelphaBot.equals(other.readOnlyJelphaBot) && userPrefs.equals(other.userPrefs)
                   && filteredTasks.equals(other.filteredTasks);
    }

}
