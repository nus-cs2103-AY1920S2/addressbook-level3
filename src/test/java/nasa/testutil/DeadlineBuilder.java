package nasa.testutil;

import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.activity.Schedule;

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
    private Date dateCreated;
    private Priority priority;
    private Schedule schedule;
    private boolean isDone;


    /**
     * Initialise default name, date and note for an activity to test.
     */
    public DeadlineBuilder() {
        name = new Name(DEFAULT_NAME);
        note = new Note(DEFAULT_NOTE);
        dueDate = new Date(DEFAULT_DUE_DATE);
        dateCreated = Date.now();
        priority = new Priority();
        schedule = new Schedule();
        isDone = false;
    }

    /**
     * Initializes the DeadlineBuilder with the data of {@code activityToCopy}.
     */
    public DeadlineBuilder(Object activityToCopy) {
        Deadline activity = (Deadline) activityToCopy;
        name = activity.getName();
        dateCreated = activity.getDateCreated();
        note = activity.getNote();
        dueDate = activity.getDueDate();
        priority = activity.getPriority();
        schedule = activity.getSchedule();
        isDone = activity.isDone();
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
    public DeadlineBuilder withDateCreated(String date) {
        this.dateCreated = new Date(date);
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
     * Sets the {@code Note} of the {@code Activity} that we are building.
     */
    public DeadlineBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Activity} that we are building.
     */
    public DeadlineBuilder withSchedule(String schedule) {
        this.schedule = new Schedule(schedule);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Activity} that we are building.
     */
    public DeadlineBuilder withIsDone(String isDone) {
        this.isDone = Boolean.parseBoolean(isDone);
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
