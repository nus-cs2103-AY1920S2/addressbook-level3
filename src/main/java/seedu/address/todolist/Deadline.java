package seedu.address.todolist;

/**
 * <h1> Deadline Class </h1>
 * Returns an extended type of Task, Deadline, where tasks have to be completed by a set date
 */

public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (" + by + ")";
    }
}
