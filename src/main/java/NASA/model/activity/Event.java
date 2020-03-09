package NASA.model.activity;

import static java.util.Objects.requireNonNull;

/**
 * Represents Event method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event extends Activity {

    private Date from;
    private Date to;

    /**
     * Initialise Event with default "deadlines".
     * Every field must be present and not null.
     */
    public Event(Date from, Date to, Note note) {
        super(new Name("event"), note);
        this.from = from;
        this.to = to;
    }

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Event(Name name, Date from, Date to, Note note) {
        super(name, note);
        this.from = from;
        this.to = to;
    }

    public Date getDateFrom() {
        return from;
    }

    public Date getDateTo() {
        return to;
    }

    public static boolean isValidEvent(Activity activity) {
        requireNonNull(activity);

        if (activity instanceof Event) {
            Event event = (Event) activity;
            return event.getDateFrom().isBefore(event.getDateTo());
        } else {
            return false;
        }
    }

}
