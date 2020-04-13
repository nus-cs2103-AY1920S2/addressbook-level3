package seedu.address.model.person;

import java.util.Comparator;

/**
 * A Comparator class for comparing and sorting EventDescriptor objects.
 */
public class DescriptorComparator implements Comparator<EventDescriptor> {

    /**
     * Compares two EventDescriptor objects. An object is considered larger
     * if it has a smaller frequency. If two objects have the same frequency,
     * then the larger object is the one with the lexicographically greater name.
     * This ensures that after sorting, the highest frequenct EventDescriptors
     * will appear first on the list; and for objects with the same frequency,
     * they will appear in alphabetical order.
     * @param d1 The first EventDescriptor object being compared.
     * @param d2 The second EventDescriptor object being compared.
     * @return 1 if d1 is larger than d2, and -1 if d2 is larger than d1.
     */
    public int compare(EventDescriptor d1, EventDescriptor d2) {
        if (d1.getFrequency() < d2.getFrequency()) {
            return 1;
        } else if (d1.getFrequency() == d2.getFrequency()
                && d1.getName().compareTo(d2.getName()) < 0) {
            return 1;
        } else {
            return -1;
        }
    }
}

