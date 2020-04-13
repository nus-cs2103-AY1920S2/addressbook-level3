package seedu.nova.model.schedule.event;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import seedu.nova.model.util.time.duration.DateTimeDuration;
import seedu.nova.model.util.time.duration.TimedDuration;
import seedu.nova.model.util.time.duration.WeekDayDuration;

/**
 * Represents an Event that can be managed.
 */
public abstract class Event implements Comparable<Event>, Serializable {
    protected String desc;
    protected String venue;
    protected LocalTime startTime;
    protected LocalTime endTime;
    protected LocalDate date;
    protected String note = "NIL";
    protected TimedDuration dtd;

    protected Event(String desc, String venue, LocalTime startTime, LocalTime endTime, LocalDate date) {
        this.desc = desc;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.dtd = new DateTimeDuration(date, startTime, endTime);
    }

    protected Event(String desc, String venue, LocalTime startTime, LocalTime endTime, DayOfWeek dow) {
        this.desc = desc;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = null;
        this.dtd = new WeekDayDuration(dow, startTime, endTime);
    }

    public abstract String getEventType();

    public String getDesc() {
        return desc;
    }

    public String getVenue() {
        return venue;
    }

    public String getNote() {
        return note;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(getDate(), getStartTime());
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public TimedDuration getDtd() {
        return dtd;
    }

    public DayOfWeek getDayOfWeek() {
        return dtd.getStartDay();
    }

    public void addNote(String note) {
        this.note = note;
    }

    @Override
    public int compareTo(Event o) {
        return getStartDateTime().compareTo(o.getStartDateTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            return desc.equals(((Event) obj).desc)
                    && date.equals(((Event) obj).date)
                    && startTime.equals(((Event) obj).startTime)
                    && endTime.equals(((Event) obj).endTime);
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public String toString() {
        return "name: " + desc + "\n" + "date: " + date + "\n" + "start time: "
                + startTime + "\n" + "end time: " + endTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(desc, date, startTime, endTime);
    }
}
