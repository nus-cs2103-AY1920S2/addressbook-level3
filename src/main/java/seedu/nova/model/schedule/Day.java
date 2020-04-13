package seedu.nova.model.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Lesson;
import seedu.nova.model.schedule.event.WeakEvent;
import seedu.nova.model.schedule.event.exceptions.EventNotFoundException;
import seedu.nova.model.schedule.event.exceptions.TimeOverlapException;
import seedu.nova.model.util.Copyable;
import seedu.nova.model.util.time.slotlist.DateTimeSlotList;

/**
 * The type Day.
 */
public class Day implements Copyable<Day> {

    private List<Event> events;
    private LocalDate date;
    private DateTimeSlotList freeSlots;

    /**
     * Instantiates a new Day.
     *
     * @param date the date
     */
    public Day(LocalDate date) {
        events = new LinkedList<>();
        this.date = date;
        freeSlots = DateTimeSlotList.ofDay(date);
    }

    private Day(List<Event> events, LocalDate date, DateTimeSlotList freeSlots) {
        this.events = events;
        this.date = date;
        this.freeSlots = freeSlots;
    }


    /**
     * Adds event.
     *
     * @param event the event
     */
    public void addEvent(Event event) {
        boolean canAdd = true;

        for (Event curr: events) {
            canAdd = checkCanAdd(event, curr);

            if (!canAdd) {
                break;
            }
        }

        if (canAdd) {
            events.add(event);
            freeSlots.excludeDuration(event.getDtd());
            Collections.sort(events);
        } else {
            throw new TimeOverlapException();
        }
    }

    /**
     * Checks if a given event overlaps with an existing event.
     * @param event the given event
     * @param curr the existing event
     * @return boolean that determines if there is no overlap
     */
    boolean checkCanAdd(Event event, Event curr) {
        boolean canAdd = true;

        LocalTime currStart = curr.getStartTime();
        LocalTime currEnd = curr.getEndTime();

        boolean haveSameStart = currStart.equals(event.getStartTime());
        boolean haveSameEnd = currEnd.equals(event.getEndTime());


        boolean eventStartIsBetween = isBetween(event.getStartTime(), currStart, currEnd);
        boolean eventEndIsBetween = isBetween(event.getEndTime(), currStart, currEnd);

        if (haveSameStart || haveSameEnd || eventStartIsBetween || eventEndIsBetween) {
            canAdd = false;
        }

        return canAdd;
    }

    /**
     * determines if a given time is in between 2 other times
     * @param time the given time
     * @param start the start time
     * @param end the end time
     * @return a boolean
     */
    boolean isBetween(LocalTime time, LocalTime start, LocalTime end) {
        int isAfterStart = time.compareTo(start);
        int isBeforeEnd = time.compareTo(end);

        return isAfterStart > 0 && isBeforeEnd < 0;
    }


    /**
     * Add lesson.
     *
     * @param lesson the lesson
     */
    public void addLesson(Lesson lesson) {
        Lesson tmp = new Lesson(lesson);
        tmp.setDate(date);
        addEvent(tmp);
    }

    /**
     * Removes an event.
     *
     * @param index index of event in the LinkedList
     */
    Event deleteEvent(int index) {
        if (index >= events.size()) {
            throw new EventNotFoundException();
        }
        Event deleted = events.remove(index);

        freeSlots.includeDuration(deleted.getDtd());
        if (deleted instanceof WeakEvent) {
            WeakEvent wkE = (WeakEvent) deleted;
            wkE.destroy();
        }
        return deleted;
    }

    /**
     * Removes an event.
     *
     * @param event event to be removed
     */
    boolean deleteEvent(Event event) {
        events.remove(event);
        freeSlots.includeDuration(event.getDtd());
        if (event instanceof WeakEvent) {
            WeakEvent wkE = (WeakEvent) event;
            wkE.destroy();
        }
        return true;
    }

    /**
     * Adds a note to an event.
     *
     * @param index index of event in the LinkedList
     * @return String representing the event with added note
     */
    public String addNote(String desc, int index) {
        if (index >= events.size()) {
            throw new EventNotFoundException();
        }
        events.get(index).addNote(desc);
        return events.get(index).toString();
    }

    public List<Event> getEventList() {
        return events;
    }


    /**
     * View the events for the day.
     *
     * @return the string of events
     */
    public String view() {

        StringBuilder sb = new StringBuilder();
        int index = 0;

        for (Event event : events) {
            sb.append(++index);
            sb.append(". ");
            sb.append(event);
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Get free slots in the form of DateTimeDuration
     *
     * @return List of DateTimeDuration
     */
    public DateTimeSlotList getFreeSlotList() {
        return freeSlots;
    }

    @Override
    public Day getCopy() {
        return new Day(new LinkedList<>(events), date, freeSlots.getCopy());
    }
}
