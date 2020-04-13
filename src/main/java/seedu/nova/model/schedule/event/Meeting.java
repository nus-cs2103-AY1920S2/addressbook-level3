package seedu.nova.model.schedule.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Represents the meeting type of Event.
 */
public class Meeting extends Event {
    private static final String EVENT_TYPE = "meeting";

    /**
     * Constructs a meeting event.
     * @param desc description of meeting
     * @param venue venue of meeting
     * @param startTime time at which meeting starts
     * @param endTime time at which meeting ends
     * @param date date of meeting
     */
    public Meeting(String desc, String venue, LocalTime startTime, LocalTime endTime, LocalDate date) {
        super(desc, venue, startTime, endTime, date);
    }

    /**
     * Constructs a meeting event with a note.
     * @param desc description of meeting
     * @param venue venue of meeting
     * @param startTime time at which meeting starts
     * @param endTime time at which meeting ends
     * @param date date of meeting
     * @param note note regarding the meeting
     */
    public Meeting(String desc, String venue, LocalTime startTime, LocalTime endTime, LocalDate date, String note) {
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
