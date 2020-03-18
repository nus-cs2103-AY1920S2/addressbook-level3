package nasa.testutil;

import static nasa.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_TUTORIAL;
import static nasa.logic.commands.CommandTestUtil.VALID_DATE_TEST;
import static nasa.logic.commands.CommandTestUtil.VALID_DATE_TEST_2;
import static nasa.logic.commands.CommandTestUtil.VALID_NOTES_TEST;

import nasa.model.activity.Activity;
import nasa.model.activity.Date;
import nasa.model.activity.Lesson;
import nasa.model.activity.Name;
import nasa.model.activity.Note;

/**
 * Class to build example lessons.
 */
public class LessonBuilder {

    public static final String DEFAULT_NAME = VALID_ACTIVITY_NAME_TUTORIAL;
    public static final String DEFAULT_DATE = "19-10-2020 03:00";
    public static final String DEFAULT_NOTE = VALID_NOTES_TEST;
    public static final String DEFAULT_START_DATE = VALID_DATE_TEST;
    public static final String DEFAULT_END_DATE = VALID_DATE_TEST_2;


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
    public Lesson build() {
        return new Lesson(name, note, startDate, endDate);
    }
}
