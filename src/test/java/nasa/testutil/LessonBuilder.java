package nasa.testutil;

import nasa.model.activity.*;

public class LessonBuilder {

    public static final String DEFAULT_NAME = "CS2103T Lesson";
    public static final String DEFAULT_DATE = "19-10-2020 03:00";
    public static final String DEFAULT_NOTE = "Sit with Kester";
    public static final String DEFAULT_START_DATE = "19-10-2020 03:00";
    public static final String DEFAULT_END_DATE = "19-12-2020 03:00";


    private Name name;
    private Date date;
    private Note note;
    private Date startDate;
    private Date endDate;

    /**
     * Initialise default name, date and note for an activity to test.
     */
    public LessonBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        note = new Note(DEFAULT_NOTE);
        startDate = new Date(DEFAULT_START_DATE);
        endDate = new Date(DEFAULT_END_DATE);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code activityToCopy}.
     */
    public LessonBuilder(Object activityToCopy) {
        Activity activity = (Activity) activityToCopy;
        name = activity.getName();
        date = activity.getDate();
        note = activity.getNote();
    }

    /**
     * Sets the {@code Name} of the {@code Activity} that we are building.
     */
    public LessonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Activity} that we are building.
     */
    public LessonBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Activity} that we are building.
     */
    public LessonBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code endDate} of the {@code Activity} that we are building.
     */
    public LessonBuilder withToDate(String startDate) {
        this.startDate = new Date(startDate);
        return this;
    }

    /**
     * Sets the {@code startDate} of the {@code Activity} that we are building.
     */
    public LessonBuilder withFromDate(String endDate) {
        this.endDate = new Date(endDate);
        return this;
    }

    /**
     * Build an activity accordingly.
     */
    public Activity build() {
        return new Lesson(name, note, startDate, endDate);
    }
}
