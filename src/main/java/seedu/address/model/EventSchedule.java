package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventList;

/**
 * Wraps all data at the EventSchedule level
 * Duplicates are not allowed by isSameEvent comparison
 */
public class EventSchedule implements ReadOnlyEventSchedule {
    private final EventList events;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        events = new EventList();
    }
    public EventSchedule() {}

    /**
     * Creates an EventList using the Events in the {@code toBeCopied}
     */
    public EventSchedule(ReadOnlyEventSchedule toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the event list with {@code Events}
     * Must not contain duplicate events
     */
    public void setEvents(ObservableList<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code EventList} with {@code newData}
     */
    public void resetData(ReadOnlyEventSchedule newData) {
        requireNonNull(newData);
        setEvents(newData.getEventsList());
    }

    /**
     * Returns true if an identical event as {@code Event} exists in the event schedule
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /** Adds an event to the event schedule; event must not already exist. */
    public void addEvent(Event event) {
        events.add(event);
    }

    /** Sorts events in the event schedule by the filter. */
    public void sortEvent(Comparator<Event> comparator) {
        events.sort(comparator);
    }

    public boolean hasClashingEvent(Event event) {
        return events.hasClashingEvent(event);
    }

    /**
     * Removes {@code key} from this {@code EventSchedule}
     * {@code key} must exist in the EventSchedule.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    public void setEvent(Event target, Event markedEvent) {
        requireNonNull(markedEvent);
        events.setEvent(target, markedEvent);
    }

    @Override
    public ObservableList<Event> getEventsList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventSchedule // instanceof handles nulls
                && events.equals(((EventSchedule) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}
