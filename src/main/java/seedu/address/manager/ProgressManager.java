package seedu.address.manager;

import seedu.address.commons.core.BaseManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
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
        postDataStorageChangeEvent(
                model.getReadOnlyAddressBook(Constants.ENTITY_TYPE.PROGRESS),
                Constants.ENTITY_TYPE.PROGRESS
        );
        logger.info(test.getList().toString());
    }

    // Called when unassign student from course
    public static void removeAllAssignmentsToOneStudent(ID courseID, ID studentID) throws CommandException {
        requireNonNull(courseID);
        requireNonNull(studentID);

        Set<ID> setOfAssignmentIDs = model.getCourse(courseID).getAssignedAssignmentsID();

        for (ID assignmentID : setOfAssignmentIDs) {
            CompositeID currProgressID = new CompositeID(assignmentID, studentID);
            model.delete(new Progress(currProgressID));
        }
    }

    // Called by AssignAssignmentToCourse
    public static void addOneAssignmentToAllStudents(Set<ID> setOfStudentIDs, ID assignmentID) throws CommandException {
        requireNonNull(setOfStudentIDs);
        requireNonNull(assignmentID);

        for (ID studentID : setOfStudentIDs) {
            CompositeID currProgressID = new CompositeID(assignmentID, studentID);
            model.add(new Progress(currProgressID));
        }
        postDataStorageChangeEvent(
            model.getReadOnlyAddressBook(Constants.ENTITY_TYPE.PROGRESS),
            Constants.ENTITY_TYPE.PROGRESS
        );
    }

    private static Progress get(ID assignmentID, ID studentID) throws CommandException {
        return model.getProgress(assignmentID, studentID);
    }

    public static Set<Progress> getProgress(ID courseID, ID studentID) throws CommandException {
        Set<Progress> allProgressOfOneStudentInOneCourse = new HashSet<>();

        Set<ID> setOfAssignmentIDs = model.getCourse(courseID).getAssignedAssignmentsID();

        for (ID assignmentID : setOfAssignmentIDs) {
            Progress curr = get(assignmentID, studentID);
            allProgressOfOneStudentInOneCourse.add(curr);
        }

        return allProgressOfOneStudentInOneCourse;
    }

    public static Set<Progress> getAllProgressOfOneCourse(ID courseID) throws CommandException {
        Set<Progress> allProgressOfStudentsInOneCourse = new HashSet<>();

        Set<ID> setOfStudentIDs = model.getCourse(courseID).getAssignedStudentsID();

        for (ID studentID : setOfStudentIDs) {
            allProgressOfStudentsInOneCourse.addAll(getProgress(courseID, studentID));
        }

        return allProgressOfStudentsInOneCourse;
    }

    public static Integer getNumberOfProgressesDone(ID courseID, ID studentID) throws CommandException {
        Set<Progress> allProgresses = ProgressManager.getProgress(courseID, studentID);
        int doneCount = 0;

        for (Progress progress : allProgresses) {
            if(progress.getIsDone()) {
                doneCount++;
            }
        }

        return doneCount;
    }

    public static void markDoneOneProgressOfOneStudent(ID assignmentID, ID studentID) throws CommandException {
        Progress targetProgress = ProgressManager.get(assignmentID, studentID);
        targetProgress.done();
        ReadOnlyAddressBookGeneric addressBook = model.getReadOnlyAddressBook(Constants.ENTITY_TYPE.PROGRESS);
        postDataStorageChangeEvent(addressBook, Constants.ENTITY_TYPE.PROGRESS);
    }

    public static void markUndoneOneProgressOfOneStudent(ID assignmentID, ID studentID) throws CommandException {
        Progress targetProgress = ProgressManager.get(assignmentID, studentID);
        targetProgress.undone();
        ReadOnlyAddressBookGeneric addressBook = model.getReadOnlyAddressBook(Constants.ENTITY_TYPE.PROGRESS);
        postDataStorageChangeEvent(addressBook, Constants.ENTITY_TYPE.PROGRESS);
    }
}
