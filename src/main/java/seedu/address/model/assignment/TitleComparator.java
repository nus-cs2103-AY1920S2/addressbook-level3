package seedu.address.model.assignment;

import java.util.Comparator;

/**
 * Compares the title of assignment and sort the in alphabetical order
 */
public class TitleComparator implements Comparator<Assignment> {
    @Override
    public int compare(Assignment a1, Assignment a2) {
        return a1.getTitle().title.compareTo(a2.getTitle().title);
    }
}
