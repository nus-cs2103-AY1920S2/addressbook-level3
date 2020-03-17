package seedu.address.todolist;

/**
 * <h1> Task Class </h1>
 * Represents general Task class such that it can be extended into more specific classes (Deadlines, To Dos)
 *  description must specify a task
 */
public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        if (isDone) {
            return "\u2713";
        } else {
            return "\u2718";
        }
    }

    public String getDescription() {
        return description;
    }

    public String markAsDone() {
        isDone = true;
        return getStatusIcon();
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
