package seedu.address.model.assignment;

import java.util.Comparator;

/**
 * Compares the title of assignment and sort the in alphabetical order
 */
public class TitleComparator implements Comparator<Assignment> {
    @Override
    public int compare(Assignment a1, Assignment a2) {
        if (a1.getStatus().status.equals(Status.ASSIGNMENT_DONE)
            && a2.getStatus().status.equals(Status.ASSIGNMENT_OUTSTANDING)) {
            return 1;
        } else if (a2.getStatus().status.equals(Status.ASSIGNMENT_DONE)
            && a1.getStatus().status.equals(Status.ASSIGNMENT_OUTSTANDING)) {
            return -1;
        } else {
            if (a1.getTitle().title.toLowerCase().compareTo(a2.getTitle().title.toLowerCase()) < 0) {
                return -1;
            } else if (a1.getTitle().title.toLowerCase().compareTo(a2.getTitle().title.toLowerCase()) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
