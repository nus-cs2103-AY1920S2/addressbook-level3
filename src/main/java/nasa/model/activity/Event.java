package nasa.model.activity;

import static java.util.Objects.requireNonNull;
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

    private Date startDate;
    private Date endDate;
    private boolean isOver;

    public Event(Name name, Date startDate, Date endDate){
        super(name);
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
        checkArgument(isValidStartDate(startDate), DATE_CONSTRAINTS);
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
        if (startDate.isBefore(this.endDate)) {
            return true;
        }
        return false;
    }

    public boolean isValidEndDate(Date endDate) {
        if (this.startDate.isBefore(endDate)) {
            return true;
        }
        return false;
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

    @Override
    public Activity deepCopy() {
        Event event = new Event(getName(), getStartDate(), getStartDate());
        event.setNote(getNote());
        event.setDateCreated(getDateCreated());
        return event;
    }
}
