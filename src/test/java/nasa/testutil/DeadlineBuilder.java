package nasa.testutil;

import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Name;
import nasa.model.activity.Note;

/**
 * Class to help build example deadlines.
 */
public class DeadlineBuilder {
    public static final String DEFAULT_NAME = "Homework";
    public static final String DEFAULT_NOTE = "Take note of qns2";
    public static final String DEFAULT_DUE_DATE = "13-11-2020 03:00";

    private Name name;
    private Note note;
    private Date dueDate;

    /**
     * Initialise default name, date and note for an activity to test.
     */
    public DeadlineBuilder() {
        name = new Name(DEFAULT_NAME);
        note = new Note(DEFAULT_NOTE);
        dueDate = new Date(DEFAULT_DUE_DATE);
    }

    /**
     * Initializes the DeadlineBuilder with the data of {@code activityToCopy}.
     */
    public DeadlineBuilder(Object activityToCopy) {
        Deadline activity = (Deadline) activityToCopy;
        name = activity.getName();
        note = activity.getNote();
        dueDate = activity.getDueDate();
    }

    /**
     * Sets the {@code Name} of the {@code Activity} that we are building.
     */
    public DeadlineBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Activity} that we are building.
     */
    public DeadlineBuilder withDueDate(String date) {
        this.dueDate = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Activity} that we are building.
     */
    public DeadlineBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Build an activity accordingly.
     */
    public Deadline build() {
        Deadline deadline = new Deadline(name, dueDate);
        deadline.setNote(note);
        return deadline;
    }

}
