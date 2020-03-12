package NASA.testutil;

import NASA.model.activity.*;

public class LessonBuilder {

    public static final String DEFAULT_NAME = "CS2103T Lesson";
    public static final String DEFAULT_DATE = "19-10-2020 03:00";
    public static final String DEFAULT_NOTE = "Sit with Kester";

    private Name name;
    private Date date;
    private Note note;

    /**
     * Initialise default name, date and note for an activity to test.
     */
    public LessonBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        note = new Note(DEFAULT_NOTE);
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
     * Build an activity accordingly.
     */
    public Activity build() {
        return new Lesson(name, date, note);
    }
}
