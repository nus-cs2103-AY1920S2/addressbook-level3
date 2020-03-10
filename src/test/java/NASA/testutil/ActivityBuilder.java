package NASA.testutil;

import NASA.model.activity.Activity;
import NASA.model.activity.Date;
import NASA.model.activity.Deadline;
import NASA.model.activity.Event;
import NASA.model.activity.Lesson;
import NASA.model.activity.Name;
import NASA.model.activity.Note;

/**
 * A utility class to help with building Activity objects.
 */
public class ActivityBuilder {

    public static final String DEFAULT_NAME = "deadline";
    public static final String DEFAULT_DATE = "19-10-2020 03:00";
    public static final String DEFAULT_NOTE = "Do homework";
    public static final String DEFAULT_TO_DATE = "03-10-2020 04:00";
    public static final String DEFAULT_FROM_DATE = "02-01-2020 05:00";

    private Name name;
    private Date date;
    private Note note;
    private Date toDate;
    private Date fromDate;

    /**
     * Initialise default name, date and note for an activity to test.
     */
    public ActivityBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        note = new Note(DEFAULT_NOTE);
        toDate = new Date(DEFAULT_TO_DATE);
        fromDate = new Date(DEFAULT_FROM_DATE);
    }

    /**
     * Initializes the ActivityBuilder with the data of {@code activityToCopy}.
     */
    public ActivityBuilder(Object activityToCopy) {
        Activity activity = (Activity) activityToCopy;
        name = activity.getName();
        date = activity.getDate();
        note = activity.getNote();
        toDate = new Date(DEFAULT_TO_DATE);
        fromDate = new Date(DEFAULT_FROM_DATE);
    }

    /**
     * Sets the {@code Name} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code toDate} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withToDate(String toDate) {
        this.toDate = new Date(toDate);
        return this;
    }

    /**
     * Sets the {@code fromDate} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withFromDate(String fromDate) {
        this.fromDate = new Date(fromDate);
        return this;
    }

    /**
     * Build an activity accordingly.
     */
    public Activity build() {
        if (name.equals(new Name("deadline"))) {
            return new Deadline(name, date, note);
        } else if (name.equals(new Name("event"))) {
            return new Event(name, fromDate, toDate, note);
        } else if (name.equals(new Name("lesson"))) {
            return new Lesson(name, date, note);
        } else {
            throw new NullPointerException("Failed to execute activity build.");
//            return new Activity(name, date, note) {
//                @Override
//                public void setName(Name name) {
//                    super.setName(new Name(" "));
//                }
//            };
        }

    }

}
