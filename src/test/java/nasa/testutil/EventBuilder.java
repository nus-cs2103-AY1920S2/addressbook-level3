package nasa.testutil;

import static nasa.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_EXAM;
import static nasa.logic.commands.CommandTestUtil.VALID_DATE_TEST;
import static nasa.logic.commands.CommandTestUtil.VALID_DATE_TEST_2;
import static nasa.logic.commands.CommandTestUtil.VALID_NOTES_TEST;

import nasa.model.activity.Activity;
import nasa.model.activity.Date;
import nasa.model.activity.Event;
import nasa.model.activity.Name;
import nasa.model.activity.Note;

/**
 * Class to build example events.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = VALID_ACTIVITY_NAME_EXAM;
    public static final String DEFAULT_DATE = "19-10-2020 03:00";
    public static final String DEFAULT_NOTE = VALID_NOTES_TEST;
    public static final String DEFAULT_FROM_DATE = VALID_DATE_TEST;
    public static final String DEFAULT_TO_DATE = VALID_DATE_TEST_2;

    private Name name;
    private Date date;
    private Note note;
    private Date toDate;
    private Date fromDate;

    /**
     * Initialise default name, date and note for an activity to test.
     */
    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        note = new Note(DEFAULT_NOTE);
        toDate = new Date(DEFAULT_TO_DATE);
        fromDate = new Date(DEFAULT_FROM_DATE);
    }

    /**
     * Initializes the EventBuilder with the data of {@code activityToCopy}.
     */
    public EventBuilder(Object activityToCopy) {
        Activity activity = (Activity) activityToCopy;
        name = activity.getName();
        date = activity.getDateCreated();
        note = activity.getNote();
        toDate = new Date(DEFAULT_TO_DATE);
        fromDate = new Date(DEFAULT_FROM_DATE);
    }

    /**
     * Sets the {@code Name} of the {@code Activity} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Activity} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Activity} that we are building.
     */
    public EventBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    /**
     * Sets the {@code toDate} of the {@code Activity} that we are building.
     */
    public EventBuilder withToDate(String toDate) {
        this.toDate = new Date(toDate);
        return this;
    }

    /**
     * Sets the {@code fromDate} of the {@code Activity} that we are building.
     */
    public EventBuilder withFromDate(String fromDate) {
        this.fromDate = new Date(fromDate);
        return this;
    }

    /**
     * Build an activity accordingly.
     */
    public Event build() {
        return new Event(name, fromDate, toDate, note);
    }
}
