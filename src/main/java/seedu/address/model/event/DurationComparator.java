package seedu.address.model.event;

import java.util.Comparator;

/**
 * Compares duration and sorts in increasing order.
 */
public class DurationComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        int duration1 = Integer.parseInt(e1.getDuration().duration);
        int duration2 = Integer.parseInt(e2.getDuration().duration);

        // equal
        if ((duration1 == duration2)) {
            return 0;
        } else if (duration1 > duration2) { // duration 1 > duration 2
            return 1;
        } else { // duration 2 < duration 1
            return -1;
        }

    }
}
