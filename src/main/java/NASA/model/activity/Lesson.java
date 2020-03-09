package NASA.model.activity;

import java.time.LocalDateTime;

/**
 * Represents Lesson method in Nasa book. New lesson re-instantiates itself after the current lesson's status is DONE.
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

    /**
     * Initialise new lesson with the new date.
     * @param numOfDaysTillNextLesson number of days from the current lesson till the next lesson
     * @return new instance of lesson at the new date
     */
    public Lesson regenerateNewLesson(int numOfDaysTillNextLesson) {
        Lesson newLesson = new Lesson(this.getName(), this.getDate().addDaysToCurrDate(numOfDaysTillNextLesson),
                this.getNote());
        return newLesson;
    }

}
