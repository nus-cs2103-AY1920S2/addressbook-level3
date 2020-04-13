// @@author Clouddoggo

package seedu.jelphabot.model.productivity;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.util.DateUtil.getDueSomedayPredicate;
import static seedu.jelphabot.commons.util.DateUtil.getDueThisWeekPredicate;
import static seedu.jelphabot.commons.util.DateUtil.getOverduePredicate;

import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.predicates.TaskDueWithinDayPredicate;

/**
 * Represents the overall productivity of the user.
 */
public class Productivity {

    private static TasksCompleted tasksCompleted;
    private static RunningTimers runningTimers;
    private static TimeSpentToday timeSpentToday;
    private final ObservableList<Task> taskList;

    // booleans to decide which sub-productivity needs to be re-rendered.
    private boolean hasNewOrEditedTasks;
    private boolean hasNewTimer;
    private boolean hasChangeInTimeSpent;

    public Productivity(ObservableList<Task> taskList, boolean hasNewOrEditedTasks, boolean hasNewTimer,
        boolean hasChangeInTimeSpent) {
        requireNonNull(taskList);
        this.taskList = taskList;
        this.hasNewOrEditedTasks = hasNewOrEditedTasks;
        this.hasNewTimer = hasNewTimer;
        this.hasChangeInTimeSpent = hasChangeInTimeSpent;

        createProductivites();
    }

    public TasksCompleted getTasksCompleted() {
        return tasksCompleted;
    }

    public RunningTimers getRunningTimers() {
        return runningTimers;
    }

    public TimeSpentToday getTimeSpentToday() {
        return timeSpentToday;
    }

    /**
     * Creates the respective productivity objects.
     */
    private void createProductivites() {
        ObservableList<Task> tasksDueToday = taskList.filtered(new TaskDueWithinDayPredicate());
        ObservableList<Task> tasksDueThisWeek = taskList.filtered(getDueThisWeekPredicate());

        if (tasksCompleted == null || hasNewOrEditedTasks) {
            tasksCompleted = new TasksCompleted(tasksDueToday, tasksDueThisWeek,
                taskList.filtered(getOverduePredicate()));
        }

        if (runningTimers == null || hasNewTimer) {
            runningTimers = new RunningTimers(taskList);
        }

        if (timeSpentToday == null || hasChangeInTimeSpent) {
            timeSpentToday = new TimeSpentToday(tasksDueToday, tasksDueThisWeek,
                taskList.filtered(getDueSomedayPredicate()));
        }
    }
}
