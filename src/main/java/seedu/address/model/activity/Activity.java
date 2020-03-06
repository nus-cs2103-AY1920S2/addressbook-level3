package seedu.address.model.activity;

/**
 * Abstract class to specify fields with getter and setters for activities.
 */
public abstract class Activity {
    private Name name;
    private Date date;
    private Note note;
    //Priority priority;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
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
                && otherActivity.getNote().equals(getNote())
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
        return otherActivity.getName().equals(getName())
                && otherActivity.getNote().equals(getNote())
                && otherActivity.getDate().equals(getDate());
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
