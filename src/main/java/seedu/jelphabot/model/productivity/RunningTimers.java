// @@author Clouddoggo

package seedu.jelphabot.model.productivity;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_NO_TIMERS;

import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Task;

/**
 * Represents the timer that is currently running for the user's task.
 */
public class RunningTimers {

    private ObservableList<Task> taskList;
    private boolean hasTimers;

    public RunningTimers(ObservableList<Task> taskList) {
        requireNonNull(taskList);
        this.taskList = taskList;
    }

    /**
     * Gets the tasks with running timers..
     * @return String representation of the tasks' Description and DateTime.
     */
    private String getTasksWithTimers() {
        StringBuilder tasksWithTimers = new StringBuilder();
        for (Task t : taskList) {
            if (t.isBeingTimed()) {
                hasTimers = true;
                tasksWithTimers.append(String.format("Task: %s %s, Deadline: %s\n", t.getModuleCode(),
                    t.getDescription(), t.getDateTime()));
            }
        }
        return tasksWithTimers.toString();
    }

    @Override
    public String toString() {
        String timers = getTasksWithTimers();
        if (!hasTimers) {
            return MESSAGE_NO_TIMERS;
        } else {
            return String.format("Timer is currently running for:\n%s", timers);
        }
    }
}
