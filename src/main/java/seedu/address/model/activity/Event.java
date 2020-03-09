package seedu.address.model.activity;

/**
 * Represents Event method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event extends Activity {

    /**
     * Initialise Event with default "event".
     * Every field must be present and not null.
     */
    public Event(Date date, Note note) {
        super(new Name("event"), date, note);
    }

    /**
     * Initialise Event with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Event(Name name, Date date, Note note) {
        super(name, date, note);
    }

}
