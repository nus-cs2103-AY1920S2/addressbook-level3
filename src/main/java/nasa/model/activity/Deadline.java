package nasa.model.activity;

import java.time.LocalDateTime;

/**
 * Represents Deadlines method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deadline extends Activity {

    private Date dueDate;

    public Deadline(Name name, Note note) {
        super(name, note);
        this.dueDate = dueDate;
    }

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Deadline(Name name, Date date, Note note, Status status, Priority priority) {
        super(name, date, note, status, priority);
    }

    public Date getDateline() {
        return dueDate;
    }

    public void updateStatus() {
        if (status == Status.ONGOING && LocalDateTime.now().isAfter(getDateline().getDate())) {
            status = Status.LATE;
        }
    }

    //TODO: detailed implementation of deadline regeneration
    public Deadline regenerate() {
        return this;
    }
}
