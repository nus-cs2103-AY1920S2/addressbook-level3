package seedu.address.manager;

import seedu.address.commons.core.BaseManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.person.CompositeID;
import seedu.address.model.person.CompositeIDBuilder;
import seedu.address.model.person.ID;

import java.util.Set;
import static java.util.Objects.requireNonNull;

public class ProgressManager extends BaseManager {
    private static Model model = ModelManager.getInstance();

    // Called by AssignStudentToCourse
    public static void addAllAssignmentsToOneStudent(Set<ID> setOfAssignmentIDs, ID studentID) throws CommandException {
        requireNonNull(setOfAssignmentIDs);
        requireNonNull(studentID);

        for (ID assignmentID : setOfAssignmentIDs) {
            CompositeID currProgressID = new CompositeIDBuilder().addAssignmentID(assignmentID).addStudentID(studentID).createCompositeID();
            model.add(new Progress(currProgressID));
        }
    }

    // Called by AssignAssignmentToCourse
    public static void addOneAssignmentToAllStudents(Set<ID> setOfStudentIDs, ID assignmentID) throws CommandException {
        requireNonNull(setOfStudentIDs);
        requireNonNull(assignmentID);

        for (ID studentID : setOfStudentIDs) {
            CompositeID currProgressID = new CompositeIDBuilder().addAssignmentID(assignmentID).addStudentID(studentID).createCompositeID();
            model.add(new Progress(currProgressID));
        }
    }
}
