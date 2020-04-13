package seedu.address.model.facilitator;

import java.util.Comparator;

/**
 * A comparator to compare 2 facilitators based on their names.
 */
public class FacilitatorNameComparator implements Comparator<Facilitator> {

    @Override
    public int compare(Facilitator facil, Facilitator otherFacil) {
        return facil.getName().fullName.compareToIgnoreCase(otherFacil.getName().fullName);
    }
}
