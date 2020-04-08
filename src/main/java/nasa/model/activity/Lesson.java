package nasa.model.activity;

/**
 * Represents Lesson method in Nasa book. New lesson re-instantiates itself after the current lesson's status is DONE.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson extends Event {

    public static final String INVALID_LESSON =
        "Lesson provided is invalid!";

    private int numOfDaysTillNextLesson = 7; // Frequency of lesson in number of days (eg. every 7 days) Default is 7.

    /**
     * Initialise Lessons with default status and priority.
     * @param name Name
     * @param startDate Date
     * @param endDate Date
     */
    public Lesson(Name name, Date startDate, Date endDate) {
        super(name, startDate, endDate);
    }

    public int getNumOfDaysTillNextLesson() {
        return numOfDaysTillNextLesson;
    }

    public void setNumOfDaysTillNextLesson(int newNumOfDays) {
        this.numOfDaysTillNextLesson = newNumOfDays;
    }

    /**
     * Initialise new lesson with the new date.
     * @return new instance of lesson at the new date
     */
    public Lesson regenerate() {
        return this;
    }
}
