package seedu.address.model.activity;

/**
 * Abstract class to provide template for activities.
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

    /*
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
     */
}
