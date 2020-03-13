package nasa.model.activity;

/**
 * Represents Event method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event extends Activity {
    private Date startDate;
    private Date endDate;

    /**
     * Initialise Event with default status and priority.
     */
    public Event(Name name, Note note, Date startDate, Date endDate) {
        super(name, note);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Initialise Event with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Event(Name name, Date date, Note note, Status status, Priority priority, Date startDate, Date endDate) {
        super(name, date, note, status, priority);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //TODO: detailed implementation of event regeneration
    public Event regenerate() {
        return this;
    }
}
