package seedu.address.model.activity;

/**
 * Represents Lesson method in Nasa book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson extends Activity {

    /**
     * Initialise Lesson with default "lesson".
     * Every field must be present and not null.
     */
    public Lesson(Date date, Note note) {
        super(new Name("lesson"), date, note);
    }

    /**
     * Initialise Lesson with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Lesson(Name name, Date date, Note note) {
        super(name, date, note);
    }

}
