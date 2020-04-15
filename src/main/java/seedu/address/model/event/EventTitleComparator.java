package seedu.address.model.event;

import java.util.Comparator;

/**
 * Compares the title of an Event and sorts in alphabetical order.
 */
public class EventTitleComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        return e1.getEventTitle().eventTitle.compareTo(e2.getEventTitle().eventTitle);
    }

}
