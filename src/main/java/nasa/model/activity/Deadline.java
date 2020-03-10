package nasa.model.activity;

/**
 * Represents Deadlines method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deadline extends Activity {

    /**
     * Initialise Deadlines with default "deadlines".
     * Every field must be present and not null.
     */
    public Deadline(Date date, Note note) {
        super(new Name("deadlines"), date, note);
    }

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Deadline(Name name, Date date, Note note, Status status, Priority priority) {
        super(name, date, note, status, priority);
    }

}
