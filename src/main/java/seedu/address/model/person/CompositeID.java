package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

import java.util.HashMap;
import seedu.address.logic.parser.Prefix;

/**
 * Represents a composite ID for objects without a field as primary key
 * but uses a tuple of IDs as a unique identifier instead such as (sid, aid) for Progress
 */


public class CompositeID extends ID {
    public final HashMap<Prefix, ID> ids;

    public CompositeID(HashMap<Prefix, ID> ids) {
        this.ids = ids;
    }

    public CompositeID(ID assignmentID, ID studentID) {
        requireAllNonNull(assignmentID, studentID);
        ids = new HashMap<>();
        addAssignmentID(assignmentID);
        addStudentID(studentID);
    }
    // no error thrown since we require for assignmentID and studentID to be non null during instantiation
    public ID getStudentID() {
        return ids.get(PREFIX_STUDENTID);
    }

    public ID getAssignmentID() {
        return ids.get(PREFIX_ASSIGNMENTID);
    }

    private void addStudentID(ID studentID) {
        ids.put(PREFIX_STUDENTID, studentID);
    }

    private void addAssignmentID(ID assignmentID)  {
        ids.put(PREFIX_ASSIGNMENTID, assignmentID);
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
