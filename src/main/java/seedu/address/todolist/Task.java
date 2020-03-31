package seedu.address.todolist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * <h1> Task Class </h1>
 * Represents general <code> Task </code> class such that it can be extended into more specific classes (Deadlines,
 *  To Dos)
 *  description must specify a task
 */

public class Task {
    private String description;
    private boolean isDone;
    private static ObservableList<Task> deadlineTaskList;

    /**
     * Constructor for task class
     * @param description describes content of task
     */

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     *
     * @return icon for status (tick or cross) to display if task is completed or not
     */
    public String getStatusIcon() {
        if (isDone) {
            return "\u2713";
        } else {
            return "\u2718";
        }
    }

    /**
     *
     * @return description of task
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return status icon of tick as task is marked as done
     */
    public String markAsDone() {
        isDone = true;
        return getStatusIcon();
    }

    public static ObservableList<Task> getNewDeadlineTaskList() {
        ArrayList<Task> deadlineTaskListDummy = new ArrayList<>();

        deadlineTaskList = FXCollections.observableArrayList(deadlineTaskListDummy);
        return deadlineTaskList;
    }

    public static ObservableList<Task> getDeadlineTaskList() {
        return deadlineTaskList;
    }

    public String getCategory() {
        return "None";
    }

    public String getDate() {
        return "None";
    }

    @Override
    public String toString() {
        return description;
    }
}
