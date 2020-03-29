package seedu.address.model.profile.course.module.personal;

import java.util.ArrayList;

/**
 * DeadlineList contains the list of deadlines and methods for the list.
 */
public class DeadlineList {
    private ArrayList<Deadline> list;

    public DeadlineList() {
        this.list = new ArrayList<Deadline>();
    }

    public DeadlineList(ArrayList<Deadline> list) {
        this.list = list;
    }

    public ArrayList<Deadline> getList() {
        return list;
    }

    /**
     * Returns specific task required.
     *
     * @param index Index of task in the list.
     * @return Required task.
     */
    public Deadline getDeadline(int index) {
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

    public void addDeadline(Deadline deadline) {
        list.add(deadline);
    }

    public void deleteDeadline(Deadline deadline) {
        list.removeIf(dl->dl.getDescription().equals(deadline.getDescription()));
    }

    public Deadline getTask(String description) {
        Deadline deadline = null;
        for (Deadline d: list) {
            if (d.getDescription().equals(description)) {
                deadline = d;
            }
        }
        return deadline;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineList && other.equals(this));
    }
}
