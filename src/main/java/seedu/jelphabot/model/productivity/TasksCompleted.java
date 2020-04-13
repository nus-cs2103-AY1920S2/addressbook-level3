// @@author Clouddoggo

package seedu.jelphabot.model.productivity;

import static seedu.jelphabot.commons.core.Messages.MESSAGE_COMPLIMENT;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_CRITICISM;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_ENCOURAGEMENT;
import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Status;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.predicates.TaskIsIncompletePredicate;

/**
 * Highlights overdue tasks if any, and mentions number of tasks completed.
 */
public class TasksCompleted {

    private ObservableList<Task> tasksDueToday;
    private ObservableList<Task> tasksDueThisWeek;
    private ObservableList<Task> overdueTasks;
    private double percentage;

    public TasksCompleted(ObservableList<Task> tasksDueToday, ObservableList<Task> tasksDueThisWeek,
        ObservableList<Task> overdueTasks) {
        requireAllNonNull(tasksDueToday, tasksDueThisWeek, overdueTasks);

        this.tasksDueToday = tasksDueToday;
        this.tasksDueThisWeek = tasksDueThisWeek;
        this.overdueTasks = overdueTasks;
        this.percentage = 1;
    }

    private String getCompletionStatus() {
        double size = tasksDueThisWeek.size() + tasksDueToday.size();
        double completed = 0;

        for (Task task : tasksDueThisWeek) {
            if (task.getStatus() == Status.COMPLETE) {
                completed++;
            }
        }

        for (Task task : tasksDueToday) {
            if (task.getStatus() == Status.COMPLETE) {
                completed++;
            }
        }

        String message = "There are no tasks to complete today!";

        if (size > 0) {
            double percentage = completed / size;
            this.percentage = percentage;

            if (percentage > 0.7) {
                message = "Great work today!";
            } else if (percentage > 0.4) {
                message = "Not bad but let's do better.";
            } else {
                message = "Wow! It must feel great to have accomplished so little today!";
            }
            return String.format("%.0f out of %.0f done.\n%s", completed,
                size, message);
        } else {
            return String.format("No tasks due today!\n%s", message);
        }
    }

    /**
     * Returns the number of incomplete overdue tasks and provides criticism or encouragement depending on the number.
     */
    private String getOverdueStatus() {
        int n = overdueTasks.filtered(new TaskIsIncompletePredicate()).size();
        StringBuilder response = new StringBuilder("There ");

        if (n > 1) {
            response.append("are ").append(n).append(" overdue tasks that are incomplete.");
        } else if (n == 1) {
            response.append("is ").append(n).append(" an overdue task that is incomplete.");
        } else {
            response.append("are no overdue tasks that are incomplete.");
        }

        return response.toString();
    }

    private String getRemark() {
        int n = overdueTasks.filtered(new TaskIsIncompletePredicate()).size();

        if (n > 3) {
            return MESSAGE_CRITICISM;
        } else if (n > 0) {
            return MESSAGE_ENCOURAGEMENT;
        } else {
            return MESSAGE_COMPLIMENT;
        }
    }

    public double getPercentage() {
        return this.percentage;
    }

    public String[] toStringArray() {
        return new String[] {getCompletionStatus(), getRemark(), getOverdueStatus()};
    }
}
