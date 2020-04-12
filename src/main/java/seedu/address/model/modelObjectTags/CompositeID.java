package seedu.address.model.modelObjectTags;

import seedu.address.logic.parser.Prefix;

import java.util.HashMap;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

/**
 * Represents a composite ID for objects without a field as primary key
 * but uses a tuple of IDs as a unique identifier instead such as (sid, aid) for Progress
 */
public class CompositeID extends ID {
    public final HashMap<Prefix, ID> ids;

    /**
     * Creates a CompositeID.
     */
    public CompositeID(HashMap<Prefix, ID> ids) {
        this.ids = ids;
    }

    /**
     * Overload CompositeID constructor.
     */
    public CompositeID(ID assignmentID, ID studentID) {
        requireAllNonNull(assignmentID, studentID);
        ids = new HashMap<>();
        addAssignmentID(assignmentID);
        addStudentID(studentID);
    }
    // no error thrown since we require for assignmentID and studentID to be non null during instantiation

    /**
     * Get Student ID of this CompositeID.
     *
     * @return student ID.
     */
    public ID getStudentID() {
        return ids.get(PREFIX_STUDENTID);
    }

    /**
     * Get Assignment ID of this CompositeID.
     *
     * @return assignment ID.
     */
    public ID getAssignmentID() {
        return ids.get(PREFIX_ASSIGNMENTID);
    }

    /**
     * Add a student ID to this CompositeID.
     *
     * @param studentID student ID to be added.
     */
    private void addStudentID(ID studentID) {
        ids.put(PREFIX_STUDENTID, studentID);
    }

    /**
     * Add a assignment ID to this CompositeID.
     *
     * @param assignmentID assignment ID to be added.
     */
    private void addAssignmentID(ID assignmentID) {
        ids.put(PREFIX_ASSIGNMENTID, assignmentID);
    }

    @Override
    public String toString() {
        return ids.toString();
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
