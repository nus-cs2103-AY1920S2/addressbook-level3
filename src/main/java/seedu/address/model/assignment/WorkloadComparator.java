package seedu.address.model.assignment;

import java.util.Comparator;

/**
 * Compares workload and sort in increasing order.
 */
public class WorkloadComparator implements Comparator<Assignment> {
    @Override
    public int compare(Assignment a1, Assignment a2) {
        if (a1.getStatus().status.equals(Status.ASSIGNMENT_DONE)
            && a2.getStatus().status.equals(Status.ASSIGNMENT_OUTSTANDING)) {
            return 1;
        } else if (a2.getStatus().status.equals(Status.ASSIGNMENT_DONE)
            && a1.getStatus().status.equals(Status.ASSIGNMENT_OUTSTANDING)) {
            return -1;
        } else {
            if (Float.parseFloat(a1.getWorkload().estHours) == Float.parseFloat(a2.getWorkload().estHours)) {
                if (a1.getDeadline().getDateTime().isEqual(a2.getDeadline().getDateTime())) {
                    return 0;
                } else if (a1.getDeadline().getDateTime().isAfter(a2.getDeadline().getDateTime())) {
                    return 1;
                } else {
                    return -1;
                }
            } else if (Float.parseFloat(a1.getWorkload().estHours) > Float.parseFloat(a2.getWorkload().estHours)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
