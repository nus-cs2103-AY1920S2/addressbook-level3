package seedu.address.model.assignment;

import java.util.Comparator;

/**
 * Compares workload and sort in increasing order.
 */
public class WorkloadComparator implements Comparator<Assignment> {
    @Override
    public int compare(Assignment a1, Assignment a2) {
        int workload1 = Integer.parseInt(a1.getWorkload().estHours);
        int workload2 = Integer.parseInt(a2.getWorkload().estHours);
        if (workload1 == workload2) {
            if (a1.getDeadline().dateTime.isEqual(a2.getDeadline().dateTime)) {
                return 0;
            } else if (a1.getDeadline().dateTime.isAfter(a2.getDeadline().dateTime)) {
                return 1;
            } else {
                return -1;
            }
        } else if (workload1 > workload2) {
            return 1;
        } else {
            return -1;
        }
    }
}
