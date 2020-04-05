package seedu.address.model.event;

import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;


/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyVEvents {
    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<VEvent> getVEvents();
}
