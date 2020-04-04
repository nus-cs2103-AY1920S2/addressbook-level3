package seedu.address.logic.commands.commandUnassign;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.commandAssign.AssignStudentToCourseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.ID;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/** This class will be in charge of assigning stuff (e.g Assignments, Teacher, etc) to a course. */
public class UnassignTeacherFromCourseCommand extends UnassignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID";
    public static final String MESSAGE_INVALID_TEACHER_ID = "There is no such teacher that with ID";
    public static final String MESSAGE_SUCCESS = "Successfully unassigned teacher %s (%s) from course %s (%s)";

    private final AssignDescriptor assignDescriptor;

    public UnassignTeacherFromCourseCommand(AssignDescriptor assignDescriptor) {
        requireNonNull(assignDescriptor);

        this.assignDescriptor = assignDescriptor;
    }

    public static boolean isValidDescriptor(AssignDescriptor assignDescriptor) {
        Set<Prefix> allAssignEntities = assignDescriptor.getAllAssignKeys();
        return allAssignEntities.contains(PREFIX_COURSEID) && allAssignEntities.contains(PREFIX_STUDENTID);
    }

    @Override
    protected CommandResult executeUndoableCommand(Model model) throws CommandException {
        // Check whether both IDs even exists
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID teacherID = this.assignDescriptor.getAssignID(PREFIX_TEACHERID);

        boolean courseExists = model.hasCourse(courseID);
        boolean teacherExists = model.hasStaff(teacherID);

        if (!courseExists) {
            throw new CommandException(MESSAGE_INVALID_COURSE_ID);
        } else if (!teacherExists) {
            throw new CommandException(MESSAGE_INVALID_TEACHER_ID);
        } else {
            Course assignedCourse = model.getCourse(courseID);
            Staff assigningStaff = model.getStaff(teacherID);

            boolean assignedCourseContainsStaff = assignedCourse.containsStaff(teacherID);
            boolean assigningStaffContainsCourse = assigningStaff.containsCourse(courseID);

            if(!assignedCourseContainsStaff) {
                throw new CommandException("This course doesn't have the specified teacher! :(");
            } else if(!assigningStaffContainsCourse) {
                throw new CommandException("The teacher isn't even assigned to this course! :(");
            } else {
                model.unassignTeacherFromCourse(teacherID, courseID);

                return new CommandResult(String.format(MESSAGE_SUCCESS,
                        assigningStaff.getName(), teacherID.value,
                        assignedCourse.getName(), courseID.value));
            }
        }
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new AssignStudentToCourseCommand(this.assignDescriptor);
    }
}
