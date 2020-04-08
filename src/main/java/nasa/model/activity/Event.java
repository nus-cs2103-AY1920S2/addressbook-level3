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

    public Event(Name name, Date startDate, Date endDate){
        super(name);
        requireAllNonNull(startDate, endDate);
        checkArgument(isValidStartEndDates(startDate, endDate), DATE_CONSTRAINTS);
        checkArgument(isValidFutureEvent(endDate), PAST_CONSTRAINTS);
        this.startDate = startDate;
        this.endDate = endDate;
        this.isOver = endDate.isBefore(Date.now());
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
        checkArgument(isValidStartEndDates(startDate, endDate), DATE_CONSTRAINTS);
        checkArgument(isValidFutureEvent(endDate), PAST_CONSTRAINTS);
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

    public boolean isValidStartDate(Date startDate) {
        requireAllNonNull(startDate);
        return startDate.isBefore(endDate);
    }

    public boolean isValidEndDate(Date endDate) {
        requireAllNonNull(endDate);
        return startDate.isBefore(endDate);
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

    /**
     * Return the difference in due date and date of creation.
     * @return int
     */
    public int getDuration() {
        return endDate.getDifference(startDate);
    }

    @Override
    public Event regenerate() {
        getSchedule().update();
        if (Date.now().isAfter(endDate) && getSchedule().getType() != 0) {
            setEndDate(getSchedule().getRepeatDate().addDaysToCurrDate(getDuration()));
            setStartDate(getSchedule().getRepeatDate());
            setDateCreated(getSchedule().getRepeatDate());
        }
        return this;
    }

    @Override
    public Activity deepCopy() {
        Event event = new Event(getName(), getStartDate(), getStartDate());
        event.setNote(getNote());
        event.setDateCreated(getDateCreated());
        return event;
    }

    public boolean isOver() {
        return isOver;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Event)) {
            return false;
        }

        Event event = (Event) other;
        return event.startDate.equals(((Event) other).startDate)
            && event.endDate.equals(((Event) other).endDate)
            && event.getNote().equals(((Event) other).getNote())
            && event.getName().equals(((Event) other).getName());
    }
}
