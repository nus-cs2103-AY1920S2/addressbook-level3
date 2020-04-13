package seedu.address.model.calender;

/**
 * <h1> To Do Class </h1>
 * Returns an extended type of Task, To Dos, where general tasks are added
 */

public class ToDo extends Task {

    /**
     * Constructor for to do class
     * @param description describes content of to do
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
