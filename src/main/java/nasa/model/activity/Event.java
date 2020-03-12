package nasa.model.activity;

import static java.util.Objects.requireNonNull;

/**
 * Represents Event method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event extends Activity {

    private Date startDate;
    private Date endDate;

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Event(Name name, Date startDate, Date endDate, Note note) {
        super(name, note);

        requireNonNull(startDate);
        requireNonNull(endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Initialise all parameters in Event.
     */
    public Event(Name name, Date date, Note note, Status status, Priority priority) {
        super(name, date, note, status, priority);
    }

    public Date getDateFrom() {
        return startDate;
    }

    public Date getDateTo() {
        return endDate;
    }

    public static boolean isValidEvent(Activity activity) {
        requireNonNull(activity);

        if (activity instanceof Event) {
            Event event = (Event) activity;
            boolean hasNotExpired = Date.now().isBefore(event.getDateTo());
            boolean isLogical = event.getDateFrom().isBefore(event.getDateTo());
            return hasNotExpired && isLogical;
        } else {
            return false;
        }
    }

    //TODO: detailed implementation of event regeneration
    public Event regenerate() {
        return this;
    }
}
