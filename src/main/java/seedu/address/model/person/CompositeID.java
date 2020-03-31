package seedu.address.model.person;

import java.util.HashMap;

/**
 * Represents a composite ID for objects without a field as primary key
 * but uses a tuple of IDs as a unique identifier instead such as (sid, aid) for Progress
 */


public class CompositeID extends ID {
    public final HashMap<String, ID> ids;

    public CompositeID(HashMap<String, ID> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return ids.toString() ;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompositeID // instanceof handles nulls
                && ids.equals(((CompositeID) other).ids)); // state check
    }

    @Override
    public int hashCode() {
        return ids.hashCode();
    }
}
