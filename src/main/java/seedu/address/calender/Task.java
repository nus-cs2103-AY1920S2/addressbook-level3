package seedu.address.calender;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.nusmodule.Priority;


/**
 * <h1> Task Class </h1>
 * Represents general <code> Task </code> class such that it can be extended into more specific classes (Deadlines,
 *  To Dos)
 *  description must specify a task
 */

public class Task {
    private static ObservableList<Task> deadlineTaskList;
    private static HashMap<String, ArrayList<Task>> deadlineTaskHashMap = new HashMap<>();
    private String description;
    private boolean isDone;
    protected Priority priority;


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

    public static void addTaskPerDate(String date, Task task) {
        if (!deadlineTaskHashMap.containsKey(date)) {
            deadlineTaskHashMap.put(date, new ArrayList<>());
            deadlineTaskHashMap.get(date).add(task);

        } else {
            deadlineTaskHashMap.get(date).add(task);
        }
    }

    public static void removeTaskPerDate(String date, Task task) {
        deadlineTaskHashMap.get(date).remove(task);


    }

    /**
     * Returns whether a specific date have any task present.
     * @param date
     * @return
     */
    public static boolean isTaskPresent(String date) {

        if (!deadlineTaskHashMap.containsKey(date)) {
            return false;
        } else {
            if (deadlineTaskHashMap.get(date).size() <= 0) {
                return false;
            }
        }
        return true;


    }

    public static HashMap<String, ArrayList<Task>> getDeadlineTaskHashMap() {
        return deadlineTaskHashMap;
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

    public String getOperation() {
        return "None";
    }

    public int getIndex() {
        return -1;
    }

    @Override
    public String toString() {
        return description;
    }

    /**
     * Check whether a date is valid.
     * @param date format of the date
     * @return true if it is a valid date.
     */
    public static boolean isValidDate(String date) {

        try {
            String[] splittedDate = date.split("-");
            int month = Integer.parseInt(splittedDate[1]);
            int day = Integer.parseInt(splittedDate[0]);

            if (month < 1 || month > 12) {
                return false;
            }

            if (day < 1 || day > 31) {
                return false;
            }

            return true;
        } catch (Exception ex) {
            return false;
        }

    }

}
