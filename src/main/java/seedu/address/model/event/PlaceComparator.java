package seedu.address.model.event;

import java.util.Comparator;

/**
 * Compares the location of an Event and sorts in alphabetical order.
 */
public class PlaceComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        return e1.getPlace().place.compareTo(e2.getPlace().place);
    }
}
