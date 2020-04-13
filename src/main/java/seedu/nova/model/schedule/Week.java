package seedu.nova.model.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Lesson;
import seedu.nova.model.schedule.event.exceptions.DateNotFoundException;
import seedu.nova.model.util.Copyable;
import seedu.nova.model.util.time.TimeUtil;

/**
 * The week class of schedule.
 */
public class Week implements Copyable<Week> {

    private final Day[] days;
    private final LocalDate startDate;
    private Set<Event> events;

    /**
     * Instantiates a new Week.
     *
     * @param date the date
     */
    public Week(LocalDate date) {
        date = TimeUtil.getMondayOfWeek(date);
        days = new Day[7];
        for (int i = 0; i < 7; i++) {
            days[i] = new Day(date.plusDays(i));
        }
        startDate = date;
        events = new TreeSet<>();
    }

    private Week(Day[] days, LocalDate startDate, Set<Event> events) {
        this.days = days;
        this.startDate = startDate;
        this.events = events;
    }

    /**
     * Adds event.
     *
     * @param event the event
     */
    public void addEvent(Event event) {
        LocalDate date = event.getDate();
        int day = date.getDayOfWeek().getValue() - 1;

        if (days[day] == null) {
            days[day] = new Day(date);
        }

        events.add(event);
        days[day].addEvent(event);
    }

    /**
     * Adds lesson.
     *
     * @param lesson the lesson
     */
    public void addLesson(Lesson lesson) {
        int day = lesson.getDay().getValue() - 1;

        if (days[day] == null) {
            days[day] = new Day(startDate.plusDays(day));
        }

        LocalDate d = startDate.plusDays(day);
        lesson.setDate(d);
        days[day].addLesson(lesson);
    }

    /**
     * gets a particular day
     *
     * @param dow day of week of the day
     * @return the day
     */
    public Day getDay(DayOfWeek dow) {
        return days[dow.getValue() - 1];
    }

    /**
     * Deletes an event
     *
     * @param date  the date of the event
     * @param index the position of event in list
     */
    public Event deleteEvent(LocalDate date, int index) {
        int day = date.getDayOfWeek().getValue() - 1;

        if (days[day] == null) {
            throw new DateNotFoundException();
        }
        Event removed = days[day].deleteEvent(index);
        events.remove(removed);
        return removed;
    }

    /**
     * deletes an event
     * @param event event to delete
     * @return successfully deleted?
     */
    public boolean deleteEvent(Event event) {
        int day = event.getDate().getDayOfWeek().getValue() - 1;

        if (days[day] == null) {
            throw new DateNotFoundException();
        }
        events.remove(event);
        return days[day].deleteEvent(event);
    }

    /**
     * Adds a note to an Event.
     *
     * @param desc  description of the note
     * @param date  the date of the event
     * @param index the position of event in list
     * @return String representing the event with added note
     */
    public String addNote(String desc, LocalDate date, int index) {
        int day = date.getDayOfWeek().getValue() - 1;

        if (days[day] == null) {
            throw new DateNotFoundException();
        }

        return days[day].addNote(desc, index);
    }

    public List<Event> getEventList() {
        List<Event> list = new LinkedList<>();

        for (Day day: days) {
            list.addAll(day.getEventList());
        }

        return list;
    }

    /**
     * View the schedule of a particular day.
     *
     * @param date the date
     * @return the string of events
     */
    public String view(LocalDate date) {

        int day = date.getDayOfWeek().getValue() - 1;
        return days[day].view();
    }

    /**
     * View the schedule of the week.
     *
     * @return the string of events
     */
    public String view() {

        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Day day : days) {

            index++;

            if (day == null) {

                continue;
            }

            String result = day.view();

            if (!result.equals("")) {

                sb.append(DayOfWeek.of(index));
                sb.append(": \n");
                sb.append(result);
                sb.append("\n");

            }
        }
        return sb.toString();
    }

    public boolean hasEvent(Event event) {
        return events.contains(event);
    }

    @Override
    public Week getCopy() {
        return new Week((Day[]) Arrays.stream(days).map(Day::getCopy).toArray(), startDate, new TreeSet<>(events));
    }

}
