package seedu.address.model.calender;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.nusmodule.ModuleTask;

/**
 * <h1> Task Class </h1>
 * Represents general <code> Task </code> class such that it can be extended into more specific classes (Deadlines,
 * To Dos)
 * description must specify a task
 */

public class Task {
    private static ObservableList<Task> deadlineTaskList;
    private static HashMap<String, ArrayList<Task>> deadlineTaskHashMap = new HashMap<>();

    protected boolean isDone;
    private String description;

    /**
     * Constructor for task class
     *
     * @param description describes content of task
     */
    public Task(String description) {
        requireAllNonNull(description);
        this.description = description;
        this.isDone = false;
    }

    /**
     * Return the status of the Task.
     *
     * @return icon for status (tick or cross) to display if task is completed or not
     */
    public boolean getStatus() {
        return isDone;
    }

    /**
     * Return the description of the task.
     *
     * @return description of task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Add Task to the date in the HashMap.
     *
     * @param date date of the task, which is the key in the HashMap
     * @param task Task that is added
     */
    public static void addTaskPerDate(String date, Task task) {
        if (!deadlineTaskHashMap.containsKey(date)) {
            deadlineTaskHashMap.put(date, new ArrayList<>());
            deadlineTaskHashMap.get(date).add(task);

        } else {
            deadlineTaskHashMap.get(date).add(task);
        }
    }

    /**
     * Remove the task from the date in the HashMap
     *
     * @param date date of the task, which is the key in the HashMap
     * @param task Task that is to be removed
     */
    public static void removeTaskPerDate(String date, Task task) {

        deadlineTaskHashMap.get(date).remove(task);
        if (deadlineTaskHashMap.get(date).size() <= 0) {
            deadlineTaskHashMap.remove(date);
        }

    }

    /**
     * Returns whether a specific date have any task present.
     *
     * @param date Key of the HashMap
     * @return true if a task is present in the date, false if not
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

    /**
     * Adds task to the calendar.
     * @param task
     */
    public static void add(Task task) {
        deadlineTaskList.add(task);
        addTaskPerDate(task.getDate(), task);
    }

    /**
     * Removes task to the calendar.
     * @param task
     */
    public static void remove(Task task) {
        deadlineTaskList.remove(task);
        removeTaskPerDate(task.getDate(), task);
    }

    public static HashMap<String, ArrayList<Task>> getDeadlineTaskHashMap() {

        return deadlineTaskHashMap;
    }

    /**
     * Mark the task as done.
     *
     * @return true when the task is marked as done
     */
    public boolean markAsDone() {
        isDone = true;
        return isDone;
    }

    /**
     * Returns a new instance of the deadline task list.
     *
     * @return an empty deadline task list
     */
    public static ObservableList<Task> getNewDeadlineTaskList() {
        ArrayList<Task> deadlineTaskListDummy = new ArrayList<>();

        deadlineTaskList = FXCollections.observableArrayList(deadlineTaskListDummy);
        return deadlineTaskList;
    }

    public static void setDeadlineTaskList(ObservableList<Task> initialiedList) {

        deadlineTaskList = initialiedList;
        for (Task task : initialiedList) {
            addTaskPerDate(task.getDate(), task);
        }
    }


    /**
     * Returns the observable list required for the UI.
     *
     * @return observable list required for the UI
     */
    public static ObservableList<Task> getDeadlineTaskList() {
        return deadlineTaskList;
    }

    /**
     * Sort the deadline task list by value specified, value can be date or priority
     *
     * @param value value specified can be date or priority
     */
    public static void sortDeadlineTaskList(String value) {

        SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy");

        if (value.equals("date")) {
            Comparator<Task> comparator = (Task o1, Task o2) -> {
                try {
                    return dateParser.parse(o1.getDate()).compareTo(dateParser.parse(o2.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return -1;
            };

            FXCollections.sort(deadlineTaskList, comparator);

        } else if (value.equals("priority")) {
            Comparator<Task> comparator = (Task o1, Task o2) -> {
                if (o1 instanceof Deadline) {
                    return 1;
                } else if (o2 instanceof Deadline) {
                    return -1;
                } else {
                    return ((ModuleTask) o1).getPriority().compareTo(((ModuleTask) o2).getPriority());
                }
            };

            FXCollections.sort(deadlineTaskList, comparator);
        } else if (value.equals("done")) {
            Comparator<Task> comparator = (Task o1, Task o2) -> {
                return Boolean.compare(o1.isDone, o2.isDone);
            };

            FXCollections.sort(deadlineTaskList, comparator);
        }

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
     *
     * @param date format of the date
     * @return true if it is a valid date.
     */
    public static boolean isValidDate(String date) {

        try {
            String[] splittedDate = date.split("-");
            int day = Integer.parseInt(splittedDate[0]);
            int month = Integer.parseInt(splittedDate[1]);

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


    /**
     * Returns true if both task have the same date and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        System.out.println(otherTask.getDescription().equals(this.description));
        System.out.println(otherTask.getDate().equals(this.getDate()));
        return otherTask.getDescription().equals(this.description)
                && otherTask.getDate().equals(this.getDate());

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description);
    }

}
