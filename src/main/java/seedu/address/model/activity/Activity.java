package seedu.address.model.activity;

/**
 * Abstract class to specify fields with getter and setters for activities.
 */
public abstract class Activity {

    private Name name;

    private Date date;

    private Note note;

    private boolean isDone;

    /**
     * Constructs a {@code activity}
     * @param name name of activity
     * @param date date of the activity
     * @param note note of the activity
     */
    public Activity(Name name, Date date, Note note) {
        this.name = name;
        this.date = date;
        this.note = note;
        this.isDone = false;
    }

    //Priority priority;

    /**
     * Retrieve the name of the activity.
     * @return String name
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the activity name to a new name.
     * Used for editing activities.
     * @param name of the activity
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Retrieve the date of the activity.
     * @return Date object representing when it is going to occur
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the activity to a new date.
     * Used for editing activities.
     * @param date of the activity
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Retrieve the note object of the activity.
     * @return Note of the activity
     */
    public Note getNote() {
        return note;
    }

    /**
     * Sets the note of the activity to a new one.
     * Used for editing activities.
     * @param note of the activity
     */
    public void setNote(Note note) {
        this.note = note;
    }

    /**
     * Sets the task to done.
     */
    public void setDone() {
        isDone = true;
    }

    /**
     * Retrieve the done status of the activity.
     * @return boolean, true means done, false means not
     */
    public boolean getIsDone() {
        return isDone;
    }

    /*
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
     */
}
