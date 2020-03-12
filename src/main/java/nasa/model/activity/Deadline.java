package nasa.model.activity;

/**
 * Represents Deadlines method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deadline extends Activity {

    /**
     * Initialise Deadlines with default status and priority.
     */
    public Deadline(Name name, Date date, Note note) {
        super(name, date, note);
    }

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Deadline(Name name, Date date, Note note, Status status, Priority priority) {
        super(name, date, note, status, priority);
    }

    //TODO: detailed implementation of deadline regeneration
    public Deadline regenerate() {
        return this;
    }
}
