package seedu.address.model.activity;

public abstract class Activity {
    Name name;
    Date date;
    /*
    Note note;
    Priority priority;
     */

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
/*
    public Note getNote() {
        return notes;
    }

    public void setNote(Note note) {
        this.note = note;
    }

 */

    /*
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
     */
}
