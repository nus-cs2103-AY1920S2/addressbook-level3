package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.ClashingEventException;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * An event is considered unique by comparing using {@code Event#isSameEvent(Event)}.
 * As such, adding and updating of events uses Event#isSameEvent(Event) for equality so as to
 * ensure that the event being added or updated is unique in terms of identity in the EventSchedule.
 *
 * @see Event#isSameEvent(Event)
 */
public class EventList {
    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public void setEvents(seedu.address.model.event.EventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setEvents(List<Event> replacement) {
        requireNonNull(replacement);
        if (!eventsAreUnique(replacement)) {
            throw new ClashingEventException();
        }
        internalList.setAll(replacement);
    }

    public void setEvent(Event target, Event markedEvent) {
        requireAllNonNull(target, markedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }
        if (!target.isSameEvent(markedEvent) && contains(markedEvent)) {
            throw new DuplicateEventException();
        }

        internalList.set(index, markedEvent);
    }

    /**
     * Adds an event to the schedule.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new ClashingEventException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns true if {@code EventSchedule} contains only unique events.
     */
    public boolean eventsAreUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameEvent(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if the list contains an equivalent event as the given event.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    /**
     * Removes the equivalent event from the Schedule.
     * The Event must exist in the Schedule.
     * @param toRemove
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    /**
     * @return size of the Event List.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Checks if there is a clashing event (determined by whether or not 2 events have the same date
     * and time.
     */
    public boolean hasClashingEvent(Event event) {
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).getEventDate().dateTime.compareTo(event.getEventDate().dateTime) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sorts the Event Schedule as an {@code ObservableList}
     */
    public void sort(Comparator<Event> comparator) {
        FXCollections.sort(internalList, comparator);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.EventList // instanceof handles nulls
                && internalList.equals(((seedu.address.model.event.EventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
