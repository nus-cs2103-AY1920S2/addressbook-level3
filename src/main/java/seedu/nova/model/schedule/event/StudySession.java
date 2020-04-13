package seedu.nova.model.schedule.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Represents the study session type of Event.
 */
public class StudySession extends Event {
    private static final String EVENT_TYPE = "study";

    /**
     * Constructs a study session event.
     * @param desc description of study session
     * @param venue venue of study session
     * @param startTime time at which study session starts
     * @param endTime time at which study session ends
     * @param date date of study session
     */
    public StudySession(String desc, String venue, LocalTime startTime, LocalTime endTime, LocalDate date) {
        super(desc, venue, startTime, endTime, date);
    }

    /**
     * Constructs a study session event with a note.
     * @param desc description of study session
     * @param venue venue of study session
     * @param startTime time at which study session starts
     * @param endTime time at which study session ends
     * @param date date of study session
     * @param note note regarding the study session
     */
    public StudySession(String desc, String venue, LocalTime startTime,
                        LocalTime endTime, LocalDate date, String note) {
        super(desc, venue, startTime, endTime, date);
        this.note = note;
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    @Override
    public String toString() {
        return "Description: " + desc + "\n"
                + "Venue: " + venue + "\n"
                + "Date/Time: " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + ", " + startTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                + " - " + endTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "\n"
                + "Note: " + note;
    }


}
