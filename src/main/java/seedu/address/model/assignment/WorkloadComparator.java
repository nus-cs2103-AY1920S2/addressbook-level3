package seedu.address.model.assignment;

import java.util.Comparator;

public class WorkloadComparator implements Comparator<Assignment> {
    @Override
    public int compare(Assignment a1, Assignment a2) {
        int workload1 = Integer.parseInt(a1.getWorkload().estHours);
        int workload2 = Integer.parseInt(a2.getWorkload().estHours);
        if (workload1 == workload2) {
            return 0;
        } else if (workload1 > workload2) {
            return 1;
        } else {
            return -1;
        }
    }
}
