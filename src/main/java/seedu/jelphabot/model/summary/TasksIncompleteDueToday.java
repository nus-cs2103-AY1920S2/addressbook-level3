//@@author eedenong
package seedu.jelphabot.model.summary;
import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Task;

/**
 * Gives an overview of the tasks that are due today.
 */
public class TasksIncompleteDueToday {
    private ObservableList<Task> tasksDueToday;

    public TasksIncompleteDueToday(ObservableList<Task> tasksDueToday) {
        requireAllNonNull(tasksDueToday);
        this.tasksDueToday = tasksDueToday;
    }

    public String getTasksDueTodayString() {
        // For each of the tasks, arrange them in the format example:
        // [ ] (CS3230) Assignment 1
        StringBuilder sb = new StringBuilder();

        for (Task task: tasksDueToday) {
            String moduleCodeString = task.getModuleCode().toString();
            String descriptionString = task.getDescription().toString();
            String toAppend = String.format("[ ] (%s) %s\n", moduleCodeString, descriptionString);
            sb.append(toAppend);
        }

        return sb.toString();
    }
}
