package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Predicate;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Observer;
import seedu.address.logic.PetManager;
import seedu.address.logic.PomodoroManager;
import seedu.address.logic.StatisticsManager;
import seedu.address.model.dayData.Date;
import seedu.address.model.dayData.DayData;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Recurring;
import seedu.address.model.task.Task;

/** Represents the in-memory model of the task list data. */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskList taskList;
    private final Pomodoro pomodoro;
    private final Statistics statistics;
    private final Pet pet;
    private final UserPrefs userPrefs;
    private final TagSet tagSet;
    private FilteredList<Task> filteredTasks;

    private PomodoroManager pomodoroManager;
    private PetManager petManager;
    private StatisticsManager statisticsManager;
    private ArrayList<Observer> observers;
    private HashMap<Task, TimerTask> recurringTimerTasks = new HashMap<>();
    private Timer recurringTimer = new Timer();
    private TaskSaver taskSaver;

    /** Initializes a ModelManager with the given taskList and userPrefs. */
    public ModelManager(
            ReadOnlyTaskList taskList,
            ReadOnlyPet pet,
            ReadOnlyPomodoro pomodoro,
            ReadOnlyStatistics statistics,
            ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(taskList, userPrefs);

        logger.fine("Initializing with Task List: " + taskList + " and user prefs " + userPrefs);

        this.taskList = new TaskList(taskList);
        this.tagSet = new TagSet(taskList);
        this.setRecurringTimers();

        Pet tempPet;

        try {
            tempPet = new Pet(pet);
        } catch (InvalidPetException e) {
            tempPet = new Pet();
            logger.info(e.toString());
        }
        this.pet = tempPet;

        this.pomodoro = new Pomodoro(pomodoro); // initialize a pomodoro as a model
        this.statistics = new Statistics(statistics); // initialize a Statistics as a model
        logger.info(String.format("Initializing with Statistics: %s", this.statistics.toString()));

        this.petManager = new PetManager();
        this.petManager.setPet(this.pet);

        this.statisticsManager = new StatisticsManager();
        this.statisticsManager.setStatistics(this.statistics);

        this.userPrefs = new UserPrefs(userPrefs);
        filteredTasks = new FilteredList<>(this.taskList.getTaskList());
        this.observers = new ArrayList<>();
    }

    public ModelManager() {
        this(new TaskList(), new Pet(), new Pomodoro(), new Statistics(), new UserPrefs());
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
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTaskListFilePath() {
        return userPrefs.getTaskListFilePath();
    }

    @Override
    public void setTaskListFilePath(Path taskListFilePath) {
        requireNonNull(taskListFilePath);
        userPrefs.setTaskListFilePath(taskListFilePath);
    }

    // =========== TaskList
    // ================================================================================

    /** Sets the task saver for saving task to storage when setTask is called. */
    @Override
    public void setTaskSaver(TaskSaver taskSaver) {
        this.taskSaver = taskSaver;
    }

    /**
     * Sets the timers required for recurring behaviour in all tasks and schedules the task in the
     * stipulated time delay. Ensures that any existing timer is canceled first.
     */
    private void setRecurringTimers() {
        this.recurringTimer.cancel();
        this.recurringTimer = new Timer();
        this.recurringTimerTasks.clear();
        for (Task t : this.taskList.getTaskList()) {
            if (t.getOptionalRecurring().isPresent()) {
                if (Recurring.shouldUpdateTask(t)) {
                    TimerTask tt = this.generateTimerTask(t);
                    recurringTimerTasks.put(t, tt);
                    this.recurringTimer.scheduleAtFixedRate(
                            tt, t.getDelayToFirstTrigger(), t.getRecurPeriod());
                }
            }
        }
    }

    /**
     * Generates the timer task for the recurring behaviour, namely updating the task.
     *
     * @param t
     * @return Timer task for timer to run.
     */
    private TimerTask generateTimerTask(Task t) {
        return new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(
                        () -> {
                            if (Recurring.shouldUpdateTask(t)) {
                                Task recurredTask = t.getRecurredTask();
                                setTask(t, recurredTask);
                                String recurredString =
                                        Recurring.MESSAGE_RECURRING_TASK_SUCCESS
                                                + recurredTask.toString();
                                notifyMainWindow(recurredString);
                            }
                        });
            }
        };
    }

    /**
     * Cancels timer task for the task given.
     *
     * @param t
     */
    private void cancelTimerTask(Task t) {
        if (this.recurringTimerTasks.containsKey(t)) {
            this.recurringTimerTasks.get(t).cancel();
        }
    }

    /**
     * Sets a timer and schedules recurring behaviour whenever task is added or saved in the model
     * if there is recurring attribute in task.
     *
     * @param t
     */
    private void setTimer(Task t) {
        if (t.getOptionalRecurring().isPresent()) {
            TimerTask tt = this.generateTimerTask(t);
            recurringTimerTasks.put(t, tt);
            this.recurringTimer.scheduleAtFixedRate(
                    tt, t.getDelayToFirstTrigger(), t.getRecurPeriod());
        }
    }

    /**
     * Sets recurring timers and thus recurring behaviour for tasks with recurring whenever taskList
     * is set in the model.
     */
    @Override
    public void setTaskList(ReadOnlyTaskList taskList) {
        this.tagSet.populateTag(taskList);
        this.taskList.resetData(taskList);
        this.setRecurringTimers();
    }

    @Override
    public String[] getTagNames() {
        return this.tagSet.getTagNames();
    }

    @Override
    public boolean hasTag(Tag t) {
        return this.tagSet.contains(t);
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return taskList;
    }

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return taskList.hasTask(task);
    }

    /** Deletes task from model and cancels corresponding timer task. */
    @Override
    public void deleteTask(Task target) {
        this.tagSet.removeTask(target);
        taskList.removeTask(target);
    }

    /** Ensures that recurring behaviour is triggered for a task when task is added to model. */
    @Override
    public void addTask(Task task) {
        taskList.addTask(task);
        this.tagSet.addTask(task);
        this.showAllTasks();
        setTimer(task);
    }

    /** sortList after task is edited so that edited task will follow the existing sort order */
    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        this.tagSet.addTask(editedTask);
        this.tagSet.removeTask(target);
        taskList.setTask(target, editedTask);
        cancelTimerTask(target);
        setTimer(editedTask);
        if (taskSaver != null) {
            this.taskSaver.saveTask(this.taskList);
        }
    }

    // =========== Subject Methods for Observer
    // ================================================================================
    public void notifyMainWindow(String input) {
        for (Observer observer : observers) {
            observer.update(input);
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // =========== Filtered Task List Methods
    // =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of @FXML
     * Serves as a reference point for TaskListPanel.java to update display {@code
     * versionedTaskList}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return this.filteredTasks;
    }

    @Override
    public void showAllTasks() {
        filteredTasks.setPredicate(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    // ================ Sort list methods

    /** Used when for the sort command when sorting by multiple fields */
    @Override
    public void setComparator(Comparator<Task> comparator, String sortOrder) {
        requireNonNull(comparator);
        requireNonNull(sortOrder);
        this.taskList.setComparator(comparator);
        this.taskList.setSortOrder(sortOrder);
    }

    /** Used when a predicate is applied to show the more relevant serach results */
    @Override
    public void setSearchResultOrder(Comparator<Task> comparator) {
        requireNonNull(comparator);
        this.taskList.setComparator(comparator);
        this.taskList.setSortOrder("");
        this.taskList.setComparator(null);
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
        return taskList.equals(other.taskList)
                && userPrefs.equals(other.userPrefs)
                && filteredTasks.equals(other.filteredTasks);
    }

    // ============================ Pet Manager

    @Override
    public ReadOnlyPet getPet() {
        return pet;
    }

    @Override
    public PetManager getPetManager() {
        return petManager;
    }

    @Override
    public void setPetName(String name) {
        this.pet.setName(name);
    }

    @Override
    public void setPetManager(PetManager petManager) {
        this.petManager = petManager;
        this.petManager.setPet(this.pet);
    }

    // ============================ Pomodoro Manager

    public ReadOnlyPomodoro getPomodoro() {
        return pomodoro;
    }

    public void setPomodoroTask(Task task) {
        this.pomodoro.setTask(task);
    }

    public Task getPomodoroTask() {
        return this.pomodoro.getRunningTask();
    }

    public void setPomodoroRestTime(float restTimeInMin) {
        this.pomodoro.setRestTime(Float.toString(restTimeInMin));
    }

    public void setPomodoroDefaultTime(float defaultTimeInMin) {
        this.pomodoro.setDefaultTime(Float.toString(defaultTimeInMin));
    }

    public void setPomodoroTimeLeft(float timeLeft) {
        this.pomodoro.setTimeLeft(Float.toString(timeLeft));
    }

    public void setPomodoroManager(PomodoroManager pomodoroManager) {
        this.pomodoroManager = pomodoroManager;
    }

    public PomodoroManager getPomodoroManager() {
        return pomodoroManager;
    }

    // ============================ Statistics Manager

    @Override
    public void setStatisticsManager(StatisticsManager statisticsManager) {
        this.statisticsManager = statisticsManager;
        this.statisticsManager.setStatistics(this.statistics);
    }

    public ReadOnlyStatistics getStatistics() {
        return statistics;
    }

    public void updateDataDatesStatistics() {
        statistics.updateDataDates();
    }

    public void updatesDayDataStatistics(DayData dayData) {
        statistics.updateDayData(dayData);
    }

    public DayData getDayDataFromDateStatistics(Date date) {
        return statistics.getDayDataFromDate(date);
    }
}
