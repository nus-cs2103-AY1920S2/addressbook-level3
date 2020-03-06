package seedu.address.model.activity;

/**
 * Represents Deadlines method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deadlines extends Activity {

    // Identity fields for deadlines
    private Name name;
    private Date date;
    private Note note;
    //private Priority priority;

    /**
     * Initialise Deadlines with default "deadlines".
     * Every field must be present and not null.
     */
    public Deadlines(String date, String note) {
        this.name = new Name("deadlines");
        this.date = new Date(date);
        this.note = new Note(note);
    }

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Deadlines(String name, String date, String note) {
        this.name = new Name(name);
        this.date = new Date(date);
        this.note = new Note(note);
    }

    /**
     * Modify name to suit user own preferences.
     */
    public void changeName(String newName) {
        this.name = new Name(newName);
    }

}
