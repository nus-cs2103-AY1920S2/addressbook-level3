package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

import java.util.HashMap;
import seedu.address.logic.commands.exceptions.CommandException;
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

    public CompositeID(ID assignmentID, ID studentID) throws CommandException {
        ids = new HashMap<>();
        addAssignmentID(assignmentID);
        addStudentID(studentID);
    }


    public ID getStudentID() {
      return ids.get(PREFIX_STUDENTID);
   }

    public ID getAssignmentID() {
      return ids.get(PREFIX_ASSIGNMENTID);
    }

    private void addStudentID(ID studentID) throws CommandException {
        if(ids.containsKey(PREFIX_STUDENTID)) {
            throw new CommandException("ID_ALREADY_EXISTS");
        } else {
            ids.put(PREFIX_STUDENTID, studentID);
        }
    }

    private void addAssignmentID(ID assignmentID) throws CommandException {
        if(ids.containsKey(PREFIX_ASSIGNMENTID)) {
            throw new CommandException("ID_ALREADY_EXISTS");
        } else {
            ids.put(PREFIX_ASSIGNMENTID, assignmentID);
        }
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
