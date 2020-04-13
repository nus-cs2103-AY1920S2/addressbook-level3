package seedu.nova.model.schedule.event;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Represents the lesson type of Event.
 */
public class Lesson extends Event {
    private static final String EVENT_TYPE = "lesson";
    private DayOfWeek day;


    /**
     * Constructs a lesson event.
     * @param description description of lesson
     * @param venue venue of lesson
     * @param startTime time at which lesson starts
     * @param endTime time at which lesson ends
     * @param date date of lesson
     * @param day day of week of lesson
     */
    public Lesson(String description, String venue, LocalTime startTime, LocalTime endTime, LocalDate date,
                   DayOfWeek day) {
        super(description, venue, startTime, endTime, date);
        this.day = day;
    }

    /**
     * Constructs a lesson event with a note.
     * @param description description of lesson
     * @param venue venue of lesson
     * @param startTime time at which lesson starts
     * @param endTime time at which lesson ends
     * @param date date of lesson
     * @param day day of week of lesson
     * @param note note related to lesson
     */
    public Lesson(String description, String venue, LocalTime startTime, LocalTime endTime,
                  DayOfWeek day, LocalDate date, String note) {
        super(description, venue, startTime, endTime, date);
        this.day = day;
        this.note = note;
    }

    /**
     * Constructs a copy of the Lesson.
     * @param lesson lesson object to be copied
     */
    public Lesson(Lesson lesson) {
        this(lesson.desc, lesson.venue, lesson.startTime, lesson.endTime, lesson.date, lesson.day);
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    public DayOfWeek getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "Description: " + desc + "\n"
                + "Venue: " + venue + "\n"
                + "Day/Time: " + day.toString() + ", "
                + startTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                + " - " + endTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "\n"
                + "Note: " + note;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            return desc.equals(((Event) obj).desc)
                    && startTime.equals(((Event) obj).startTime)
                    && endTime.equals(((Event) obj).endTime)
                    && date.equals(((Event) obj).date)
                    && day.equals(((Lesson) obj).day);
        } else {
            return super.equals(obj);
        }
    }
}
