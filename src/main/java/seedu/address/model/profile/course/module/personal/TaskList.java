package seedu.address.model.profile.course.module.personal;

import java.util.ArrayList;

/**
 * TaskList contains the list of tasks and methods for the list.
 */
public class TaskList {
    private ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    public ArrayList<Task> getList() {
        return list;
    }

    /**
     * Returns specific task required.
     *
     * @param index Index of task in the list.
     * @return Required task.
     */
    public Task getTask(int index) {
        return list.get(index);
    }

    /**
     * Returns number of tasks in list.
     *
     * @return size of list.
     */
    public int getListSize() {
        return list.size();
    }

    public void addTask(Task task) {
        list.add(task);
    }

    public void deleteTask(Task task) {
        list.remove(task);
    }
}
