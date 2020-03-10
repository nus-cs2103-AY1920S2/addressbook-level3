package nasa.model.activity;

/**
 * Represents Lesson method in Nasa book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson extends Activity {

    /**
     * Initialise Event with default "lesson".
     * Every field must be present and not null.
     */
    public Lesson(Date date, Note note) {
        super(new Name("lesson"), date, note);
    }

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Lesson(Name name, Date date, Note note) {
        super(name, date, note);
    }

}
