//@@author eedenong
package seedu.jelphabot.model.summary;

import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Task;

/**
 * Gives an overview of the tasks that were completed today (within the day).
 */
public class TasksCompletedToday {
    private ObservableList<Task> tasksCompletedToday;

    public TasksCompletedToday(ObservableList<Task> tasksCompletedToday) {
        requireAllNonNull(tasksCompletedToday);
        this.tasksCompletedToday = tasksCompletedToday;
    }

    public String getTasksCompletedTodayString() {
        // For each of the tasks, arrange them in the format example:
        // [ ] (CS3230) Assignment 1
        StringBuilder sb = new StringBuilder();

        for (Task task: tasksCompletedToday) {
            String moduleCodeString = task.getModuleCode().toString();
            String descriptionString = task.getDescription().toString();
            String toAppend = String.format("[X] (%s) %s\n", moduleCodeString, descriptionString);
            sb.append(toAppend);
        }

        return sb.toString();
    }

}
