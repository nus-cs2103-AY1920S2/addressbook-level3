package nasa.model.activity;

/**
 * Represents Event method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event extends Activity {

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Event(Name name, Date date, Note note, Status status, Priority priority) {
        super(name, date, note, status, priority);
    }

    //TODO: detailed implementation of event regeneration
    public Event regenerate() {
        return this;
    }
}
