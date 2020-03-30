package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a composite ID for objects without a field as primary key but uses a tuple as a unique identifier instead
 */


public class CompositeID extends ID {
    List<ID> listOfIDs;

    public void CompositeID(ID... ids) {
        listOfIDs = new ArrayList<ID>();

        for(ID x : ids) {
            listOfIDs.add(x);
        }
    }
}
