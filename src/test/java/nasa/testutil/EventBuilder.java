package nasa.testutil;

import nasa.model.activity.*;

public class EventBuilder {

    public static final String DEFAULT_NAME = "MPSH";
    public static final String DEFAULT_DATE = "19-10-2020 03:00";
    public static final String DEFAULT_NOTE = "BasketBall CCA";
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
        date = activity.getDate();
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
    public Activity build() {
        return new Event(name, note, fromDate, toDate);
    }
}
