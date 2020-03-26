package nasa.model.activity;

import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import nasa.model.Regenerable;

/**
 * Abstract class to specify fields with getter and setters for activities.
 */
public abstract class Activity implements Regenerable<Activity> {

    protected Status status;
    protected Name name;
    protected Date date;
    protected Note note;
    protected Priority priority;

    public Activity(Name name, Note note) {
        requireAllNonNull(name);
        this.name = name;
        this.note = note;
        this.date = Date.now();
        this.status = Status.ONGOING;
        this.priority = new Priority("1");
    }

    public Activity(Name name, Note note, Priority priority) {
        requireAllNonNull(name);
        this.name = name;
        this.note = note;
        this.date = Date.now();
        this.priority = priority;
        this.status = Status.ONGOING;
    }

    /**
     * Constructs a {@code activity}
     * @param name name of activity
     * @param date date of the activity
     * @param note note of the activity
     * @param status status of the activity
     * @param priority priority of the activity
     */
    public Activity(Name name, Date date, Note note, Status status, Priority priority) {
        requireAllNonNull(name, date, note, status, priority);
        this.name = name;
        this.date = date;
        this.note = note;
        this.status = Status.ONGOING;
        this.priority = priority;
    }

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

    public Status getStatus() {
        return status;
    }

    /**
     * Returns true if both activities of the same name, note and date.
     * @param otherActivity
     * @return
     */
    public boolean isSameActivity(Activity otherActivity) {
        if (otherActivity == this) {
            return true;
        }

        return otherActivity != null
                && otherActivity.getName().equals(getName())
                //&& otherActivity.getNote().equals(getNote())
                && otherActivity.getDate().equals(getDate());
    }

    /**
     * Returns true if both activities have the same identity and data fields.
     * This defines a stronger notion of equality between two activities.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Activity)) {
            return false;
        }
        Activity otherActivity = (Activity) other;
        return otherActivity.getName().equals(getName());
    }

    /**
     * Retrieve the late status of the activity
     * @return boolean, true means late, false means not
     */
    public boolean isLate() {
        return status == Status.LATE;
    }

    /**
     * Sets the task to done.
     */
    public void setDone() {
        status = Status.DONE;
    }

    /**
     * Retrieve the done status of the activity.
     * @return boolean, true means done, false means not
     */
    public boolean isDone() {
        return status == Status.DONE;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Regenerate activity based on set rules and logic.
     * @return new instance of the activity, with its attributes possibly modified
     */
    public abstract Activity regenerate();

    public void updateStatus() {}

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, note, date, status, priority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Note: ")
                .append(getNote())
                .append(" Date: ")
                .append(getDate())
                .append(" Status: ")
                .append(getStatus())
                .append(" Priority: ")
                .append(getPriority());
        return builder.toString();
    }
}
