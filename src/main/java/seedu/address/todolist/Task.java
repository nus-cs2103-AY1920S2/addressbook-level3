package seedu.address.todolist;

/**
 * <h1> Task Class </h1>
 * Represents general <code> Task </code> class such that it can be extended into more specific classes (Deadlines,
 *  To Dos)
 *  description must specify a task
 */

public class Task {
    private String description;
    private boolean isDone;

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

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
