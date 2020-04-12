package seedu.address.manager;

import seedu.address.commons.core.BaseManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.DeleteEntityEvent;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;

import java.util.Set;
import java.util.logging.Logger;

public class EdgeManager extends BaseManager {
    private static Model model = ModelManager.getInstance();
    private static final Logger logger = LogsCenter.getLogger(EdgeManager.class);

    public static final String INVALID_ENTITY = "Handle delete entity in edge manager not valid object";

    // ========================== For Assigning of X TO Y =========================

    public static void assignStudentToCourse(ID studentID, ID courseID) throws CommandException {
        Course foundCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Student foundStudent = (Student) model.get(studentID, Constants.ENTITY_TYPE.STUDENT);

        foundCourse.addStudent(studentID);
        foundStudent.addCourse(courseID);
        model.updateFilteredCourseList(model.PREDICATE_SHOW_ALL_COURSES);
        model.updateFilteredStudentList(model.PREDICATE_SHOW_ALL_STUDENTS);

        // todo: change this after merge details-view (with different factory strategy)
        model.set(foundCourse, foundCourse);
        model.set(foundStudent, foundStudent);
    }

    public static void assignAssignmentToCourse(ID assignmentID, ID courseID) throws CommandException {
        Course foundCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Assignment foundAssignment = (Assignment) model.get(assignmentID, Constants.ENTITY_TYPE.ASSIGNMENT);
        logger.info(foundCourse.toString());
        foundCourse.addAssignment(assignmentID);
        foundAssignment.addCourseID(courseID);

        model.set(foundCourse, foundCourse);
        model.set(foundAssignment, foundAssignment);
    }

    public static void assignTeacherToCourse(ID staffID, ID courseID) throws CommandException {
        Course foundCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Staff foundTeacher = (Staff) model.get(staffID, Constants.ENTITY_TYPE.STAFF);

        foundCourse.addStaff(staffID);
        foundTeacher.addCourse(courseID);
        model.set(foundCourse, foundCourse);
        model.set(foundTeacher, foundTeacher);
    }

    // =============================================================================

    // ========================== For Unassigning of X FROM Y =========================

    public static void unassignAssignmentFromCourse(ID assignmentID, ID courseID) throws CommandException {
        Course foundCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Assignment foundAssignment = (Assignment) model.get(assignmentID, Constants.ENTITY_TYPE.ASSIGNMENT);

        foundCourse.removeAssignment(assignmentID);
        foundAssignment.removeCourseID(courseID);

        model.set(foundCourse, foundCourse);
        model.set(foundAssignment, foundAssignment);
    }

    public static void unassignStudentFromCourse(ID studentID, ID courseID) throws CommandException {
        Course foundCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Student foundStudent = (Student) model.get(studentID, Constants.ENTITY_TYPE.STUDENT);

        foundCourse.removeStudent(studentID);
        foundStudent.removeCourse(courseID);
        model.updateFilteredCourseList(model.PREDICATE_SHOW_ALL_COURSES);
        model.updateFilteredStudentList(model.PREDICATE_SHOW_ALL_STUDENTS);

        model.set(foundCourse, foundCourse);
        model.set(foundStudent, foundStudent);
    }

    public static void unassignTeacherFromCourse(ID teacherID, ID courseID) throws CommandException {
        Course foundCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Staff foundStaff = (Staff) model.get(teacherID, Constants.ENTITY_TYPE.STAFF);

        foundCourse.removeStaff();
        foundStaff.removeCourse(courseID);

        model.set(foundCourse, foundCourse);
        model.set(foundStaff, foundStaff);
    }

    // =======================================================================================================

    // ========================= Handle delete entities ======================================================
    public static void handleDeleteEntityEvent(DeleteEntityEvent event) {
        try {
            if (event.entityType == Constants.ENTITY_TYPE.COURSE) {
                Course course = (Course) model.getAddressBook(Constants.ENTITY_TYPE.COURSE).get(event.entityID);
                processEdgeFromCourse(course, true);
            } else if (event.entityType == Constants.ENTITY_TYPE.ASSIGNMENT) {
                Assignment assignment = (Assignment) (model.getAddressBook(Constants.ENTITY_TYPE.ASSIGNMENT).get(event.entityID));
                processEdgeFromAssignment(assignment, true);
            } else if (event.entityType == Constants.ENTITY_TYPE.STUDENT) {
                Student student = (Student) model.getAddressBook(Constants.ENTITY_TYPE.STUDENT).get(event.entityID);
                processEdgeFromStudent(student, true);
            } else if (event.entityType == Constants.ENTITY_TYPE.STAFF) {
                Staff staff = (Staff) (model.getAddressBook(Constants.ENTITY_TYPE.STAFF).get(event.entityID));
                processEdgeFromStaff(staff, true);
            }
        } catch (CommandException e) {
            System.out.println(INVALID_ENTITY);
        }
    }

    public static void revokeEdgesFromDeleteEvent(ModelObject deletedObject) {
        try {
            Constants.ENTITY_TYPE entityType = model.modelObjectToEntityType(deletedObject);
            if (entityType == Constants.ENTITY_TYPE.COURSE) {
                processEdgeFromCourse((Course) deletedObject, false);
            } else if (entityType == Constants.ENTITY_TYPE.ASSIGNMENT) {
                processEdgeFromAssignment((Assignment) deletedObject, false);
            } else if (entityType == Constants.ENTITY_TYPE.STUDENT) {
                processEdgeFromStudent((Student) deletedObject, false);
            } else if (entityType == Constants.ENTITY_TYPE.STAFF) {
                processEdgeFromStaff((Staff) deletedObject, false);
            }
        } catch (CommandException e) {
            System.out.println("Revoke edges of not valid object");
        }
    }

    private static void processEdgeFromCourse(Course course, Boolean isDelete) {
        try {
            // Delete edges to assignments
            Set<ID> assignmentIDs = course.getAssignedAssignmentsID();
            for (ID assignmentID : assignmentIDs) {
                try {
                    if (isDelete) {
                        unassignAssignmentFromCourse(assignmentID, course.getId());
                    } else {
                        assignAssignmentToCourse(assignmentID, course.getId());
                    }
                } catch (Exception e) {
                }
            }

            // Delete edges to student
            Set<ID> studentIDs = course.getAssignedStudentsID();
            for (ID studentID : studentIDs) {
                try {
                    if (isDelete) {
                        unassignStudentFromCourse(studentID, course.getId());
                    } else {
                        assignStudentToCourse(studentID, course.getId());
                    }
                } catch (Exception e) {
                }
            }
            ID teacherID = course.getAssignedStaffID();
            if (isDelete) {
                unassignTeacherFromCourse(teacherID, course.getId());
            } else {
                assignTeacherToCourse(teacherID, course.getId());
            }
        } catch (Exception e) {
        }
    }

    private static void processEdgeFromAssignment(Assignment assignment, Boolean isDelete) {
        try {
            // Delete edges to course
            ID courseID = assignment.getAssignedCourseID();
            if (isDelete) {
                unassignAssignmentFromCourse(assignment.getId(), courseID);
            } else {
                assignAssignmentToCourse(assignment.getId(), courseID);
            }
        } catch (Exception e) {
            String a = "1";
        }
    }

    private static void processEdgeFromStudent(Student student, Boolean isDelete) {
        try {
            // Delete edges to student
            Set<ID> courseIDs = student.getAssignedCoursesID();
            for (ID courseID : courseIDs) {
                try {
                    if (isDelete) {
                        unassignStudentFromCourse(student.getId(), courseID);
                    } else {
                        assignStudentToCourse(student.getId(), courseID);
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    private static void processEdgeFromStaff(Staff staff, Boolean isDelete) {
        try {
            // Delete edges to assignments
            Set<ID> courseIDs = staff.getAssignedCoursesID();
            for (ID courseID : courseIDs) {
                try {
                    if (isDelete) {
                        unassignTeacherFromCourse(staff.getId(), courseID);
                    } else {
                        assignTeacherToCourse(staff.getId(), courseID);
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            String a = "1";
        }
    }

    // =======================================================================================================

}
