package seedu.address.model.event;

import java.util.List;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyEvents {
    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    List<Event> getEvents();
}
