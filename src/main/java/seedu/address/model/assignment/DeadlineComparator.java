package seedu.address.model.assignment;

import java.util.Comparator;

/**
 * Compares deadline and sort them in chronological order.
 */
public class DeadlineComparator implements Comparator<Assignment> {
    @Override
    public int compare(Assignment a1, Assignment a2) {
        if (a1.getDeadline().dateTime.isEqual(a2.getDeadline().dateTime)) {
            if (Integer.parseInt(a1.getWorkload().estHours) == Integer.parseInt(a2.getWorkload().estHours)) {
                return 0;
            } else if (Integer.parseInt(a1.getWorkload().estHours) > Integer.parseInt(a2.getWorkload().estHours)) {
                return 1;
            } else {
                return -1;
            }
        } else if (a1.getDeadline().dateTime.isAfter(a2.getDeadline().dateTime)) {
            return 1;
        } else {
            return -1;
        }
    }
}
