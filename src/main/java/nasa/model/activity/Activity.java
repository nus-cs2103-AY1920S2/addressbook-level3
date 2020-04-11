package nasa.model.activity;

import static nasa.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Abstract class to specify fields with getter and setters for activities.
 */
public abstract class Activity {

    public static final String EMPTY_NOTE_STRING = "-";

    private Name name;
    private Note note;
    private Date dateCreated;
    private Schedule schedule;

    /**
     * Constructor to create an activity.
     * @param name Name of the activity
     */
    public Activity(Name name) {
        requireAllNonNull(name);
        this.name = name;
        dateCreated = Date.now();
        note = new Note(EMPTY_NOTE_STRING);
        schedule = new Schedule(dateCreated);
    }


    /**
     * Constructor to create an activity.
     * @param name Name of the activity
     */
    public Activity(Name name, Date dateCreated, Note note) {
        requireAllNonNull(name);
        this.name = name;
        this.dateCreated = dateCreated;
        this.note = note;
        schedule = new Schedule(dateCreated);
    }

    /**
     * Method to set the note of the activity.
     * @param note
     */
    public void setNote(Note note) {
        requireAllNonNull(note);
        this.note = note;
    }

    /**
     * Method to get the note of the activity.
     * @return Note
     */
    public Note getNote() {
        return this.note;
    }

    /**
     * Method to get the name of the activity.
     * @return Name
     */
    public Name getName() {
        return name;
    }

    /**
     * Method to the set the name of the activity.
     * @param name
     */
    public void setName(Name name) {
        requireAllNonNull(name);
        this.name = name;
    }

    /**
     * Method to get the dateCreated of the activity.
     * @return dateCreated
     */
    public Date getDateCreated() {
        return this.dateCreated;
    }

    /**
     * Method to the set the dateCreated of the activity.
     * @param dateCreated
     */
    public void setDateCreated(Date dateCreated) {
        requireAllNonNull(dateCreated);
        this.dateCreated = dateCreated;
    }

    public Date getScheduleDate() {
        return schedule.getRepeatDate();
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(int type) {
        schedule.setType(type, dateCreated);
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Method to check if the activity occurs in that month.
     * @param month Month currently in
     * @return true if occurs else false
     */
    public abstract boolean occurInMonth(int month);

    public abstract Activity deepCopy();

    public abstract void regenerate();
}
