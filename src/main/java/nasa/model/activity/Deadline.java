package nasa.model.activity;

import static nasa.commons.util.AppUtil.checkArgument;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents Deadlines method in NASA.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deadline extends Activity {
    public static final String DATE_CONSTRAINTS =
        "Deadline has already passed.";

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
        checkArgument(isValidDeadline(dueDate), DATE_CONSTRAINTS);
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
     * @param priority Priority
     * @param dueDate Date
     */
    public Deadline(Name name, Date date, Note note, Priority priority, Date dueDate, boolean isDone) {
        super(name, date, note);
        requireAllNonNull(priority, dueDate);
        this.priority = priority;
        this.dueDate = dueDate;
        this.isDone = isDone;
        isOverdue = isOverdue();
    }

    /**
     * Method to return due date of the deadline.
     * @return dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Return the difference in due date and date of creation.
     * @return int
     */
    public int getDifferenceInDay() {
        return dueDate.getDifference(getDateCreated());
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

    /**
     * Unmark deadline as done, and set {@code isOverdue} if overdue.
     */
    public void unmarkAsDone() {
        isDone = false;
        if (isOverdue()) {
            isOverdue = true;
        } else {
            isOverdue = false;
        }
    }

    public boolean isOverdue() {
        return !isDone && Date.now().isAfter(dueDate);
    }

    @Override
    public void setSchedule(int type) {
        getSchedule().setType(type, dueDate);
    }

    @Override
    public boolean occurInMonth(int month) {
        int dueDateMonth = this.dueDate.getDate().getMonth().getValue();
        return month == dueDateMonth;
    }

    @Override
    public Activity deepCopy() {
        Name nameCopy = new Name(getName().toString());
        Date dueDateCopy = new Date(getDueDate().toString());
        Note noteCopy = new Note(getNote().toString());
        Date dateCreatedCopy = Date.acceptPastDate(getDateCreated().toString());
        Priority priorityCopy = new Priority(getPriority().toString());
        Deadline copy = new Deadline(nameCopy, dateCreatedCopy, noteCopy, priorityCopy, dueDateCopy, isDone);
        if (isDone) {
            copy.markAsDone();
        }
        copy.setSchedule(getSchedule().getDeepCopy());
        return copy;
    }

    @Override
    public void regenerate() {
        getSchedule().update();
        if (Date.now().isAfter(dueDate) && getSchedule().getType() != 0) {
            setDueDate(getSchedule().getRepeatDate());
            setDateCreated(getSchedule().getRepeatDate());
        }
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isValidDeadline(Date dueDate) {
        return !(dueDate.isBefore(Date.now()));
    }

    /**
     * Returns true if both are the same deadline with same deadline attributes.
     * This defines a stronger notion of equality between two deadlines.
     */
    public boolean isSameDeadline(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;
        return otherDeadline.getName().equals(getName())
                && otherDeadline.getDueDate().equals(getDueDate())
                && otherDeadline.getPriority().equals(getPriority())
                && otherDeadline.getDateCreated().equals(getDateCreated())
                && otherDeadline.getNote().equals(getNote());
    }

}
