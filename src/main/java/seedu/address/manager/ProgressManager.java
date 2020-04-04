package seedu.address.manager;

import seedu.address.commons.core.BaseManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.person.CompositeID;
import seedu.address.model.person.ID;

import java.util.HashSet;
import java.util.logging.Logger;

import java.util.Set;
import static java.util.Objects.requireNonNull;

public class ProgressManager extends BaseManager {
    private static Model model = ModelManager.getInstance();
    private static final Logger logger = LogsCenter.getLogger(ProgressManager.class);

    // Called by AssignStudentToCourse
    public static void addAllAssignmentsToOneStudent(Set<ID> setOfAssignmentIDs, ID studentID) throws CommandException {
        requireNonNull(setOfAssignmentIDs);
        requireNonNull(studentID);

        for (ID assignmentID : setOfAssignmentIDs) {
            CompositeID currProgressID = new CompositeID(assignmentID, studentID);
            model.add(new Progress(currProgressID));
        }

        ReadOnlyAddressBookGeneric<Progress> test =  model.getProgressAddressBook();
        logger.info(test.getList().toString());
    }

    // Called by AssignAssignmentToCourse
    public static void addOneAssignmentToAllStudents(Set<ID> setOfStudentIDs, ID assignmentID) throws CommandException {
        requireNonNull(setOfStudentIDs);
        requireNonNull(assignmentID);

        for (ID studentID : setOfStudentIDs) {
            CompositeID currProgressID = new CompositeID(assignmentID, studentID);
            model.add(new Progress(currProgressID));
        }
    }

    private static Progress get(ID assignmentID, ID studentID) throws CommandException {
        return model.getProgress(assignmentID, studentID);
    }

    public static Set<Progress> getProgress(ID courseID, ID studentID) throws CommandException {
        Set<Progress> allProgressOfStudentInOneCourse = new HashSet<>();

        Set<ID> setOfAssignmentIDs = model.getCourse(courseID).getAssignedAssignmentsID();

        for (ID assignmentID : setOfAssignmentIDs) {
            Progress curr = get(assignmentID, studentID);
            allProgressOfStudentInOneCourse.add(curr);
        }

        return allProgressOfStudentInOneCourse;
    }

    public static String getNumberOfProgressesDone(ID courseID, ID studentID) throws CommandException {
        Set<Progress> allProgresses = ProgressManager.getProgress(courseID, studentID);
        String output = "%s : %s";
        int totalNumProgresses = allProgresses.size();

        int doneCount = 0;

        for (Progress progress : allProgresses) {
            if(progress.getIsDone()) {
                doneCount++;
            }
        }

        return String.format(output, doneCount, totalNumProgresses);
    }
}
