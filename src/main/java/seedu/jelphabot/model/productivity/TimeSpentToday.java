// @@author Clouddoggo

package seedu.jelphabot.model.productivity;

import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;

import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.TimeSpent;

/**
 * Represents the user's productivity for the day
 */
public class TimeSpentToday {

    private ObservableList<Task> tasksDueToday;
    private ObservableList<Task> tasksDueThisWeek;
    private ObservableList<Task> remainingTasks;

    public TimeSpentToday(ObservableList<Task> tasksDueToday, ObservableList<Task> tasksDueThisWeek,
        ObservableList<Task> remainingTasks) {
        requireAllNonNull(tasksDueToday, tasksDueThisWeek, remainingTasks);
        this.tasksDueToday = tasksDueToday;
        this.tasksDueThisWeek = tasksDueThisWeek;
        this.remainingTasks = remainingTasks;
    }

    private TimeSpent getTimeSpent(ObservableList<Task> taskList) {
        TimeSpent result = new TimeSpent(Duration.ZERO);
        for (Task task : taskList) {
            result.addTime(task.getTimeSpent());
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("Tasks due today: %s.\nTasks due in next 6 days: %s.\nTasks due after 6 days: %s.",
            getTimeSpent(tasksDueToday), getTimeSpent(tasksDueThisWeek), getTimeSpent(remainingTasks));
    }
}
