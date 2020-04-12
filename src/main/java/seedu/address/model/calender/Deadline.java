package seedu.address.model.calender;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * <h1> Deadline Class </h1>
 * Returns an extended type of Task, Deadline, where tasks have to be completed by a set date
 */

public class Deadline extends Task {

    public static final String MESSAGE_CONSTRAINTS = "Please enter valid Date in the format DD-MM-YYYY";
    protected String by;
    protected String category;
    protected String operation;
    protected int index;


    /**
     * Constructor for deadline class
     * @param description describes content of task
     * @param by sets targeted completion date of task
     */
    public Deadline(String description, String by, String category, String operation) {
        super(description);
        requireAllNonNull(operation);
        this.by = by;
        this.category = category;
        this.operation = operation;
    }

    public Deadline(int index, String operation) {
        super("Delete task");
        requireAllNonNull(operation);
        this.index = index;
        this.operation = operation;
    }

    /**
     * dummy docs.
     * @param date
     * @return
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

    @Override
    public String getDate() {
        return this.by;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public String getOperation() {
        return this.operation;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + by + ")" + "(" + category + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherTask = (Deadline) other;
        System.out.println(otherTask.getDescription().equals(getDescription()));
        System.out.println(otherTask.getDate().equals(this.getDate()));
        return otherTask.getDescription().equals(getDescription())
                && otherTask.getDate().equals(this.getDate());

    }
}
