package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the location at which the Event will be held.
 * Guarantees: immutable
 */
public class Place {

    // Instance variable
    public final String place;

    public Place (String place) {
        requireAllNonNull(place);
        this.place = place;
    }

    @Override
    public String toString() {
        return place;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.event.Place // instanceof handles nulls
                && place.equals(((seedu.address.model.event.Place) other).place)); // state check
    }
}
