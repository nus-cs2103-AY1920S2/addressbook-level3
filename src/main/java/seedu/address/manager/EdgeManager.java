package seedu.address.manager;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.BaseManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.DeleteEntityEvent;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.commandAssign.AssignAssignmentToCourseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelAssignment.AssignmentAddressBook;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.ID;
import java.util.logging.Logger;


import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class EdgeManager extends BaseManager {
    private static Model model = ModelManager.getInstance();
    private static final Logger logger = LogsCenter.getLogger(EdgeManager.class);


    // ========================== For Assigning of X TO Y =========================

    public static void assignStudentToCourse(ID studentID, ID courseID) throws CommandException {
        Course foundCourse = model.getCourse(courseID);
        Student foundStudent = model.getStudent(studentID);

        foundCourse.addStudent(studentID);
        foundStudent.addCourse(courseID);
        foundCourse.processAssignedStudents(
                (FilteredList<Student>) model.getFilteredStudentList());
        foundStudent.processAssignedCourses(
                (FilteredList<Course>) model.getFilteredCourseList());
        model.updateFilteredCourseList(model.PREDICATE_SHOW_ALL_COURSES);
        model.updateFilteredStudentList(model.PREDICATE_SHOW_ALL_STUDENTS);

        // todo: change this after merge details-view (with different factory strategy)
        model.set(foundCourse, foundCourse);
        model.set(foundStudent, foundStudent);
    }

    public static void assignAssignmentToCourse(ID assignmentID, ID courseID) throws CommandException {
        Course foundCourse = model.getCourse(courseID);
        Assignment foundAssignment = model.getAssignment(assignmentID);
        logger.info(foundCourse.toString());
        foundCourse.addAssignment(assignmentID);
        foundAssignment.addCourseID(courseID);

        model.set(foundCourse, foundCourse);
        model.set(foundAssignment, foundAssignment);
    }

    public static void assignTeacherToCourse(ID staffID, ID courseID) throws CommandException {
        Course foundCourse = model.getCourse(courseID);
        Staff foundTeacher = model.getStaff(staffID);

        foundCourse.assignStaff(staffID);
        foundTeacher.addCourse(courseID);

        foundCourse.processAssignedStaff(
                (FilteredList<Staff>) model.getFilteredStaffList());
        foundTeacher.processAssignedCourses(
                (FilteredList<Course>) model.getFilteredCourseList());
        model.set(foundCourse, foundCourse);
        model.set(foundTeacher, foundTeacher);
    }

    // =============================================================================

    // ========================== For Unassigning of X FROM Y =========================

    public static void unassignAssignmentFromCourse(ID assignmentID, ID courseID) throws CommandException {
        Course foundCourse = model.getCourse(courseID);
        Assignment foundAssignment = model.getAssignment(assignmentID);

        foundCourse.removeAssignment(assignmentID);
        foundAssignment.removeCourseID(courseID);

        model.set(foundCourse, foundCourse);
        model.set(foundAssignment, foundAssignment);
    }

    public static void unassignStudentFromCourse(ID studentID, ID courseID) throws CommandException {
        Course foundCourse = model.getCourse(courseID);
        Student foundStudent = model.getStudent(studentID);

        foundCourse.removeStudent(studentID);
        foundStudent.removeCourse(courseID);
        foundCourse.processAssignedStudents(
                (FilteredList<Student>) model.getFilteredStudentList());
        foundStudent.processAssignedCourses(
                (FilteredList<Course>) model.getFilteredCourseList());
        model.updateFilteredCourseList(model.PREDICATE_SHOW_ALL_COURSES);
        model.updateFilteredStudentList(model.PREDICATE_SHOW_ALL_STUDENTS);

        model.set(foundCourse, foundCourse);
        model.set(foundStudent, foundStudent);
    }

    public static void unassignTeacherFromCourse(ID teacherID, ID courseID) throws CommandException {
        Course foundCourse = model.getCourse(courseID);
        Staff foundStaff = model.getStaff(teacherID);

        foundCourse.removeStaff();
        foundStaff.removeCourse(courseID);

        model.set(foundCourse, foundCourse);
        model.set(foundStaff, foundStaff);
    }

    // =======================================================================================================

    // ========================= Handle delete entities ======================================================
    public static void handleDeleteEntityEvent(DeleteEntityEvent event) {
        if (event.entityType == Constants.ENTITY_TYPE.COURSE) {
            deleteEdgeFromCourse(event.entityID);
        } else if (event.entityType == Constants.ENTITY_TYPE.ASSIGNMENT) {
            deleteEdgeFromAssignment(event.entityID);
        } else if (event.entityType == Constants.ENTITY_TYPE.STUDENT) {
            deleteEdgeFromStudent(event.entityID);
        }
    }

    private static void deleteEdgeFromCourse(ID courseID) {
        try {
            Course course = (Course) model.getAddressBook(Constants.ENTITY_TYPE.COURSE).get(courseID);

            // Delete edges to assignments
            Set<ID> assignmentIDs = course.getAssignedAssignmentsID();
            for (ID assignmentID : assignmentIDs) {
                try {
                    unassignAssignmentFromCourse(assignmentID, courseID);
                } catch (Exception e) {
                }
            }

            // Delete edges to student
            Set<ID> studentIDs = course.getAssignedStudentsID();
            for (ID studentID : studentIDs) {
                try {
                    unassignStudentFromCourse(studentID, courseID);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    private static void deleteEdgeFromAssignment(ID assignmentID) {
        try {
            AssignmentAddressBook assignmentAddressBook = (AssignmentAddressBook)model.getAddressBook(Constants.ENTITY_TYPE.ASSIGNMENT);
            Assignment assignment = (Assignment) (model.getAddressBook(Constants.ENTITY_TYPE.ASSIGNMENT).get(assignmentID));

            // Delete edges to course
            ID courseID = assignment.getAssignedCourseID();
            unassignAssignmentFromCourse(assignmentID, courseID);
        } catch (Exception e) {
            String a = "1";
        }
    }

    private static void deleteEdgeFromStudent(ID studentID) {
        try {
            Student student = (Student) model.getAddressBook(Constants.ENTITY_TYPE.STUDENT).get(studentID);

            // Delete edges to student
            Set<ID> courseIDs = student.getAssignedCoursesID();
            for (ID courseID : courseIDs) {
                try {
                    unassignStudentFromCourse(studentID, courseID);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    // =======================================================================================================

}
