package seedu.jelphabot.model;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.jelphabot.commons.core.GuiSettings;
import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.predicates.TaskDueWithinDayPredicate;
import seedu.jelphabot.model.task.predicates.TaskIsCompletedPredicate;
import seedu.jelphabot.model.task.predicates.TaskIsIncompletePredicate;
import seedu.jelphabot.model.task.predicates.TaskDueWithinWeekPredicate;
import seedu.jelphabot.model.task.UniqueTaskList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final JelphaBot addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyJelphaBot addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new JelphaBot(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
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
    public void setJelphaBotFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setJelphaBotFilePath(addressBookFilePath);
    }

    // =========== JelphaBot
    // ================================================================================

    @Override
    public ReadOnlyJelphaBot getJelphaBot() {
        return addressBook;
    }

    @Override
    public void setJelphaBot(ReadOnlyJelphaBot addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return addressBook.hasTask(task);
    }

    @Override
    public boolean hasTimingTask() {
        return addressBook.hasTaskBeingTimed();
    }

    @Override
    public void deleteTask(Task target) {
        addressBook.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        addressBook.setTask(target, editedTask);
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

    public ObservableList<Task> getFilteredByIncompleteTaskList() {
        TaskIsIncompletePredicate taskIncompletePredicate = new TaskIsIncompletePredicate();
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        FilteredList<Task> filteredIncompleteList = new FilteredList<>(filteredTasks, taskIncompletePredicate);
        uniqueTaskList.setTasks(filteredIncompleteList);
        return uniqueTaskList.asUnmodifiableObservableList();
    }

    public ObservableList<Task> getFilteredByIncompleteDueTodayTaskList() {
        TaskIsIncompletePredicate taskIncompletePredicate = new TaskIsIncompletePredicate();
        TaskDueWithinDayPredicate taskWithinDayPredicate = new TaskDueWithinDayPredicate();
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        FilteredList<Task> filteredIncompleteList = new FilteredList<>(filteredTasks, taskIncompletePredicate);
        FilteredList<Task> filteredIncompleteDueTodayList = new FilteredList<>(filteredIncompleteList,
            taskWithinDayPredicate);
        uniqueTaskList.setTasks(filteredIncompleteDueTodayList);
        return uniqueTaskList.asUnmodifiableObservableList();
    }

    public ObservableList<Task> getFilteredByCompleteTaskList() {
        TaskIsCompletedPredicate predicate = new TaskIsCompletedPredicate();
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        FilteredList<Task> filteredList = new FilteredList<>(filteredTasks, predicate);
        uniqueTaskList.setTasks(filteredList);
        return uniqueTaskList.asUnmodifiableObservableList();
    }

    // public ObservableList<Productivity> getFilteredProductivityList() {
    //     // pass the list into productivity
    //     Productivity prod = new Productivity(filteredTasks);
    //
    // }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook) && userPrefs.equals(other.userPrefs)
                && filteredTasks.equals(other.filteredTasks);
    }

}
