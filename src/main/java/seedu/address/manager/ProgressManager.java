package seedu.address.manager;

import seedu.address.commons.core.BaseManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelObjectTags.CompositeID;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelProgress.Progress;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class ProgressManager extends BaseManager {
    private static Model model = ModelManager.getInstance();
    private static final Logger logger = LogsCenter.getLogger(ProgressManager.class);

    // Called by AssignStudentToCourse
    public static void addAllProgressesToOneStudent(ID courseID, ID studentID) throws CommandException {
        requireAllNonNull(courseID, studentID);
        Course selectedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Set<ID> allAssignmentInCourse = selectedCourse.getAssignedAssignmentsID();

        for (ID assignmentID : allAssignmentInCourse) {
            CompositeID currProgressID = new CompositeID(assignmentID, studentID);
            model.add(new Progress(currProgressID));
        }

        postDataStorageChangeEvent(
                model.getReadOnlyAddressBook(Constants.ENTITY_TYPE.PROGRESS),
                Constants.ENTITY_TYPE.PROGRESS
        );
    }

    // Called by UnassignStudentFromCourse
    public static void removeAllProgressesFromOneStudent(ID courseID, ID studentID) throws CommandException {
        requireNonNull(courseID);
        requireNonNull(studentID);

        Course selectedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Set<ID> setOfAssignmentIDs = selectedCourse.getAssignedAssignmentsID();

        for (ID assignmentID : setOfAssignmentIDs) {
            model.removeProgress(assignmentID, studentID);
        }

        postDataStorageChangeEvent(
                model.getReadOnlyAddressBook(Constants.ENTITY_TYPE.PROGRESS),
                Constants.ENTITY_TYPE.PROGRESS
        );
    }

    public static Set<Progress> getAllProgressesForOneStudent(ID courseID, ID studentID) throws CommandException {
        requireAllNonNull(courseID, studentID);

        Set<Progress> allProgressOfOneStudentInOneCourse = new HashSet<>();
        Course selectedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Set<ID> setOfAssignmentIDs = selectedCourse.getAssignedAssignmentsID();

        for (ID assignmentID : setOfAssignmentIDs) {
            Progress curr = get(assignmentID, studentID);
            allProgressOfOneStudentInOneCourse.add(curr);
        }

        return allProgressOfOneStudentInOneCourse;
    }

    // Called by AssignAssignmentToCourse
    public static void addOneProgressToAllStudents(ID courseID, ID assignmentID) throws CommandException {
        requireAllNonNull(courseID, assignmentID);

        Course selectedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Set<ID> allStudentsInCourse = selectedCourse.getAssignedStudentsID();

        for (ID studentID : allStudentsInCourse) {
            CompositeID currProgressID = new CompositeID(assignmentID, studentID);
            model.add(new Progress(currProgressID));
        }

        postDataStorageChangeEvent(
                model.getReadOnlyAddressBook(Constants.ENTITY_TYPE.PROGRESS),
                Constants.ENTITY_TYPE.PROGRESS
        );
    }

    // called by UnassignAssignmentFromCourse
    public static void removeOneProgressFromAllStudents(ID courseID, ID assignmentID) throws CommandException {
        requireAllNonNull(courseID, assignmentID);

        Course selectedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Set<ID> allStudentsInCourse = selectedCourse.getAssignedStudentsID();

        for (ID studentID : allStudentsInCourse) {
            model.removeProgress(assignmentID, studentID);
        }

        postDataStorageChangeEvent(
                model.getReadOnlyAddressBook(Constants.ENTITY_TYPE.PROGRESS),
                Constants.ENTITY_TYPE.PROGRESS
        );
    }

    public static Set<Progress> getOneProgressFromAllStudents(ID courseID, ID assignmentID) throws CommandException {
        requireAllNonNull(courseID, assignmentID);

        Course selectedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Set<ID> allStudentsInCourse = selectedCourse.getAssignedStudentsID();
        Set<Progress> oneProgressOfAllStudentInOneCourse = new HashSet<>();

        for (ID studentID : allStudentsInCourse) {
            Progress targetProgress = model.getProgress(assignmentID, studentID);
            oneProgressOfAllStudentInOneCourse.add(targetProgress);
        }

        postDataStorageChangeEvent(
                model.getReadOnlyAddressBook(Constants.ENTITY_TYPE.PROGRESS),
                Constants.ENTITY_TYPE.PROGRESS
        );

        return oneProgressOfAllStudentInOneCourse;
    }

    public static void addUndoProgress(Set<Progress> progresses) throws CommandException {
        requireNonNull(progresses);

        for (Progress progress : progresses) {
            model.add(progress);
        }
    }

    /*
    public static Set<Progress> getAllProgressOfOneCourse(ID courseID) throws CommandException {
        Set<Progress> allProgressOfStudentsInOneCourse = new HashSet<>();

        Set<ID> setOfStudentIDs = model.getCourse(courseID).getAssignedStudentsID();

        for (ID studentID : setOfStudentIDs) {
            allProgressOfStudentsInOneCourse.addAll(getProgress(courseID, studentID));
        }

        return allProgressOfStudentsInOneCourse;
    }
    */
    public static Integer getNumberOfProgressesDone(ID courseID, ID studentID) throws CommandException {
        Set<Progress> allProgresses = ProgressManager.getAllProgressesForOneStudent(courseID, studentID);
        int doneCount = 0;

        for (Progress progress : allProgresses) {
            if (progress.getIsDone()) {
                doneCount++;
            }
        }

        return doneCount;
    }

    public static void markDoneOneProgressOfOneStudent(ID assignmentID, ID studentID) throws CommandException {
        Progress targetProgress = ProgressManager.get(assignmentID, studentID);
        targetProgress.done();
        model.set(targetProgress, targetProgress);
    }

    public static void markUndoneOneProgressOfOneStudent(ID assignmentID, ID studentID) throws CommandException {
        Progress targetProgress = ProgressManager.get(assignmentID, studentID);
        targetProgress.undone();
        model.set(targetProgress, targetProgress);
    }

    private static Progress get(ID assignmentID, ID studentID) throws CommandException {
        return model.getProgress(assignmentID, studentID);
    }
}
