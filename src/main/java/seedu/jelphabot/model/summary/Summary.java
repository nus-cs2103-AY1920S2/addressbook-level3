//@@author eedenong
//files in the summary package was adapted from clouddoggo's productivity package.
package seedu.jelphabot.model.summary;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.predicates.TaskCompletedWithinDayPredicate;
import seedu.jelphabot.model.task.predicates.TaskDueWithinDayAndIncompletePredicate;

/**
 * Represents a Summary for the user.
 */
public class Summary {
    private final ObservableList<Task> taskList;

    private TasksIncompleteDueToday tasksIncompleteDueToday;
    private TasksCompletedToday tasksCompletedToday;

    public Summary(ObservableList<Task> taskList) {
        requireNonNull(taskList);
        this.taskList = taskList;
        populateSummary();
    }

    public TasksIncompleteDueToday getTasksIncompleteDueToday() {
        return tasksIncompleteDueToday;
    }

    public TasksCompletedToday getTasksCompletedToday() {
        return tasksCompletedToday;
    }

    /**
     * populates the respective Summary objects.
     */
    private void populateSummary() {
        ObservableList<Task> dueTodayTaskList = taskList.filtered(new TaskDueWithinDayAndIncompletePredicate());
        ObservableList<Task> completedTodayTaskList = taskList.filtered(new TaskCompletedWithinDayPredicate());
        this.tasksIncompleteDueToday = new TasksIncompleteDueToday(dueTodayTaskList);
        this.tasksCompletedToday = new TasksCompletedToday(completedTodayTaskList);
    }
}

