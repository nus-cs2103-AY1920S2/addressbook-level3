package seedu.address.model.tag;

import java.util.Comparator;

/**
 * Comparator to compare tags alphabetically.
 */
public class TagComparator implements Comparator<Tag> {
    @Override
    public int compare(Tag o1, Tag o2) {
        return o1.toString().compareTo(o2.toString());
    }
}
