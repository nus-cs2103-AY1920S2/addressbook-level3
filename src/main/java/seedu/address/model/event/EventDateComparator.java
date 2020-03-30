package seedu.address.model.event;

import java.util.Comparator;

/**
 * Compares event date and sorts them in chronological order.
 */
public class EventDateComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        // equal; we don't need to compare duration because no 2 event dates and times
        // should be the same! (can add an assert statement here to check)
        if (e1.getEventDate().getDateTime().isEqual(e2.getEventDate().getDateTime())) {
            return 0;
        } else if (e1.getEventDate().getDateTime().isAfter(e2.getEventDate().getDateTime())) {
            return 1; // e1 date is after e2 date
        } else {
            return -1;
        }
    }
}
