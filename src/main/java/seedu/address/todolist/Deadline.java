package seedu.address.todolist;

/**
 * <h1> Deadline Class </h1>
 * Returns an extended type of Task, Deadline, where tasks have to be completed by a set date
 */

public class Deadline extends Task {

    protected String by;
    public static final String MESSAGE_CONSTRAINTS = "Please enter valid Date in the format DD-MM-YYYY";
    protected String category;

    /**
     * Constructor for deadline class
     * @param description describes content of task
     * @param by sets targeted completion date of task
     */
    public Deadline(String description, String by, String category) {
        super(description);
        this.by = by;
        this.category = category;
    }

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
    public String toString() {
        return super.toString() + " (" + by + ")" + "(" + category + ")";
    }
}
