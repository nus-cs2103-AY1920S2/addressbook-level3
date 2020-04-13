package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

/**
 * Represents the list of events that enforces uniqueness in its events.
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Checks whether the list contains a given event.
     *
     * @param toCheck a group to check
     * @return boolean status
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    /**
     * Returns true if the list contains an equivalent eventList as the given argument.
     */
    public boolean containsEvents(List<Event> toCheck) {
        requireNonNull(toCheck);
        boolean eventExists;
        for (Event oneEvent : toCheck) {
            eventExists = internalList.stream().anyMatch(oneEvent::isSameEvent);
            if (eventExists) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an event to the list. The event must not already exist in the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds given events to the list if they are already not in the list.
     * @param toAdd events to be added.
     */
    public void addAll(List<Event> toAdd) {
        requireNonNull(toAdd);
        if (containsEvents(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.addAll(toAdd);
    }

    public void setEvents(List<Event> events) {
        requireNonNull(events);
        if (!eventsAreUnique(events)) {
            throw new DuplicateEventException();
        }
        internalList.setAll(events);
    }

    /**
     * Removes the equivalent person from the list. The person must exist in the list.
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && internalList.equals(((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code groups} contains only unique persons.
     */
    private boolean eventsAreUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameEvent(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
