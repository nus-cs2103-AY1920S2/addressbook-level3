package nasa.model.activity;

import static nasa.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents Deadlines method in NASA.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deadline extends Activity {

    private Date dueDate;
    private Priority priority;

    private boolean isDone;
    private boolean isOverdue;

    /**
     * Constructor to create a new deadline.
     * @param name Name of deadline
     * @param dueDate date the deadline is due
     */
    public Deadline(Name name, Date dueDate) {
        super(name);
        requireAllNonNull(dueDate);
        this.dueDate = dueDate;
        priority = new Priority();
        isDone = false;
        isOverdue = isOverdue();
    }

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     * @param name Name
     * @param date Date
     * @param note Note
     * @param status Status
     * @param priority Priority
     * @param dueDate Date
     */
    public Deadline(Name name, Date date, Note note, Priority priority, Date dueDate) {
        super(name, date, note);
        this.priority = priority;
        this.dueDate = dueDate;
    }

    /**
     * Method to return due date of the deadline.
     * @return dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Method to set the dueDate.
     */
    public void setDueDate(Date dueDate) {
        requireAllNonNull(dueDate);
        this.dueDate = dueDate;
    }

    /**
     * Method to get the priority.
     * @return
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Method to set the priority.
     * @param priority
     */
    public void setPriority(Priority priority) {
        requireAllNonNull(priority);
        this.priority = priority;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        isDone = false;
        if (isOverdue()) {
            isOverdue = true;
        } else {
            isOverdue = false;
        }
    }

    private boolean isOverdue() {
        return !isDone && Date.now().isAfter(dueDate);
    }

    @Override
    public boolean occurInMonth(int month) {
        int dueDateMonth = this.dueDate.getDate().getMonth().getValue();
        return month == dueDateMonth;
    }

    @Override
    public Activity deepCopy() {
        Deadline copy = new Deadline(getName(), getDueDate());
        copy.setDateCreated(getDateCreated());
        copy.setPriority(priority);
        copy.setNote(getNote());
        if (isDone) {
            copy.markAsDone();
        }
        return copy;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
