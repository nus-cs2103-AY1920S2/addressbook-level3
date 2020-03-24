package seedu.address.model.assignment;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Compares deadline and sort them in chronological order.
 */
public class DeadlineComparator implements Comparator<Assignment> {
    @Override
    public int compare(Assignment a1, Assignment a2) {
        //LocalDateTime dt1 = LocalDateTime.of(a1.getDeadline().date, parsedTime)
        if (a1.getDeadline().getDateTime().isEqual(a2.getDeadline().getDateTime())) {
            if (Integer.parseInt(a1.getWorkload().estHours) == Integer.parseInt(a2.getWorkload().estHours)) {
                return 0;
            } else if (Integer.parseInt(a1.getWorkload().estHours) > Integer.parseInt(a2.getWorkload().estHours)) {
                return 1;
            } else {
                return -1;
            }
        } else if (a1.getDeadline().getDateTime().isAfter(a2.getDeadline().getDateTime())) {
            return 1;
        } else {
            return -1;
        }
    }
}
