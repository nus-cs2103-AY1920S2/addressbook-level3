package seedu.nova.model.schedule.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Represents the consultation type of Event.
 */
public class Consultation extends Event {
    private static final String EVENT_TYPE = "consultation";

    /**
     * Constructs a consultation event.
     * @param desc description of consultation
     * @param venue venue of consultation
     * @param startTime time at which consultation starts
     * @param endTime time at which consultation ends
     * @param date date of consultation
     */
    public Consultation(String desc, String venue, LocalTime startTime, LocalTime endTime, LocalDate date) {
        super(desc, venue, startTime, endTime, date);
    }

    /**
     * Constructs a consultation event with a note.
     * @param desc description of consultation
     * @param venue venue of consultation
     * @param startTime time at which consultation starts
     * @param endTime time at which consultation ends
     * @param date date of consultation
     * @param note note regarding the consultation
     */
    public Consultation(String desc, String venue, LocalTime startTime,
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
