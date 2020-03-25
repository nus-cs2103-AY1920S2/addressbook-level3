package nasa.model.activity;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.AppUtil.checkArgument;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents Deadlines method in Nasa Book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deadline extends Activity {

    public static final String DUE_DATE_CONSTRAINTS =
            "Deadline should be after date of creation.";

    private Date dueDate;

    public Deadline(Name name, Date dueDate) {
        super(name);
        requireNonNull(dueDate);
        checkArgument(isExpiredDueDate(dueDate), DUE_DATE_CONSTRAINTS);
        this.dueDate = dueDate;
    }

    public Deadline(Name name, Date dueDate, Note note) {
        super(name, note);
        requireNonNull(dueDate);
        checkArgument(isExpiredDueDate(dueDate), DUE_DATE_CONSTRAINTS);
        this.dueDate = dueDate;
    }

    public Deadline(Name name, Note note, Priority priority, Date dueDate) {
        super(name, note, priority);
        requireAllNonNull(name, dueDate);
        this.dueDate = dueDate;
    }

    /**
     * Initialise Deadlines with a particular unique {@code name}.
     * Every field must be present and not null.
     */
    public Deadline(Name name, Date date, Note note, Status status, Priority priority, Date dueDate) {
        super(name, date, note, status, priority);
        this.dueDate = dueDate;
    }

    public Date getDateline() {
        return dueDate;
    }

    public void setDateLine(Date date) {
        this.dueDate = date;
        updateStatus();
    }

    public int getDifferenceInDay() {
        return (int) getDate().getDifference(dueDate)[0];
    }

    /**
     * Get days remaining for the task.
     */
    public int getDaysRemaining() {
        return (int) this.dueDate.getDifference(Date.now())[0];
    }

    @Override
    public void updateStatus() {
        if (status == Status.ONGOING && Date.now().isAfter(getDateline())) {
            status = Status.LATE;
        }
    }

    public static boolean isExpiredDueDate(Date date) {
        return date.isAfter(Date.now());
    }

    //TODO: detailed implementation of deadline regeneration
    public Deadline regenerate() {
        if (super.getSchedule().update()) {
            setDateLine(getDateline().addDaysToCurrDate(getDifferenceInDay()));
        }
        return this;
    }
}
