package nasa.model.activity;

import static nasa.commons.util.AppUtil.checkArgument;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents Event method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event extends Activity {
    public static final String INVALID_EVENT =
        "Event provided is invalid!";
    public static final String DATE_CONSTRAINTS =
        "Start date should be before end date.";
    public static final String PAST_CONSTRAINTS =
        "The event has already passed.";

    private Date startDate;
    private Date endDate;
    private boolean isOver;

    public Event(Name name, Date startDate, Date endDate) {
        super(name);
        requireAllNonNull(startDate, endDate);
        checkArgument(isValidStartEndDates(startDate, endDate), DATE_CONSTRAINTS);
        checkArgument(isValidFutureEvent(endDate), PAST_CONSTRAINTS);
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOver = isOver();
    }

    /**
     * This constructor is use for testing.
     * @param name Name of event
     * @param startDate Start date of event
     * @param endDate End date of event
     * @param note Notes
     */
    public Event(Name name, Date startDate, Date endDate, Note note) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOver = isOver();
        super.setNote(note);
    }

    /**
     * Initialise Event with a particular unique {@code name}.
     * Every field must be present and not null.
     * @param name Name
     * @param date Date
     * @param note Note
     * @param startDate Date
     * @param endDate Date
     */
    public Event(Name name, Date date, Note note, Date startDate, Date endDate) {
        super(name, date, note);
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOver = isOver();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        requireAllNonNull(startDate);
        checkArgument(isValidStartDate(startDate), DATE_CONSTRAINTS);
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        requireAllNonNull(endDate);
        checkArgument(isValidEndDate(endDate), DATE_CONSTRAINTS);
        this.endDate = endDate;
    }

    /**
     * Returns true if start date is valid (ie. start date is after end date)
     * @param startDate
     * @return
     */
    public boolean isValidStartDate(Date startDate) {
        requireAllNonNull(startDate);
        return startDate.isBefore(endDate) || startDate.isEqual(endDate);
    }

    /**
     * Returns true if end date is valid (ie. end date is after start date)
     * @param endDate
     * @return
     */
    public boolean isValidEndDate(Date endDate) {
        requireAllNonNull(endDate);
        return startDate.isBefore(endDate) || startDate.isEqual(endDate);
    }

    @Override
    public boolean occurInMonth(int month) {
        int startDateMonth = this.startDate.getDate().getMonth().getValue();
        int endDateMonth = this.endDate.getDate().getMonth().getValue();
        for (int i = startDateMonth; i <= endDateMonth; i++) {
            if (i == month) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidStartEndDates(Date start, Date end) {
        return !(end.isBefore(start));
    }

    public boolean isValidFutureEvent(Date end) {
        return (!end.isBefore(Date.now()));
    }

    public boolean isOver() {
        return endDate.isBefore(Date.now());
    }

    /**
     * Return the difference in due date and date of creation.
     * @return int
     */
    public int getDuration() {
        return endDate.getDifference(startDate);
    }

    @Override
    public void setSchedule(int type) {
        getSchedule().setType(type, endDate);
    }

    @Override
    public void regenerate() {
        getSchedule().update();
        if (Date.now().isAfter(endDate) && getSchedule().getType() != 0) {
            int timeDiff = getDuration();
            setEndDate(getSchedule().getRepeatDate().addDaysToCurrDate(timeDiff));
            setStartDate(endDate);
            setDateCreated(getSchedule().getRepeatDate());
        }
    }

    @Override
    public Activity deepCopy() {
        Name nameCopy = new Name(getName().toString());
        Date startDateCopy = new Date(getStartDate().toString());
        Date endDateCopy = new Date(getEndDate().toString());
        Note noteCopy = new Note(getNote().toString());
        Date dateCreatedCopy = new Date(getDateCreated().toString());
        Schedule scheduleCopy = new Schedule();
        scheduleCopy.setDefaultDate(dateCreatedCopy);
        scheduleCopy.setRepeatDate(getScheduleDate());
        scheduleCopy.setType(getSchedule().getType());
        Event eventCopy = new Event(nameCopy, dateCreatedCopy, noteCopy, startDateCopy, endDateCopy);
        eventCopy.setSchedule(scheduleCopy);
        return eventCopy;
    }

    /**
     * Returns true if both are the same event with same event attributes.
     * This defines a stronger notion of equality between two events.
     */
    public boolean isSameEvent(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getStartDate().equals(getStartDate())
                && otherEvent.getEndDate().equals(getEndDate())
                && otherEvent.getDateCreated().equals(getDateCreated())
                && otherEvent.getNote().equals(getNote());
    }

}
