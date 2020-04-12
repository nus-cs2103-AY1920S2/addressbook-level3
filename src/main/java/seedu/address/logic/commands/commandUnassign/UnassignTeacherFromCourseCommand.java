package seedu.address.logic.commands.commandUnassign;

import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.commandAssign.AssignTeacherToCourseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.manager.EdgeManager;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelStaff.Staff;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * This class will be in charge of assigning stuff (e.g Assignments, Teacher, etc) to a course.
 */
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
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID staffID = this.assignDescriptor.getAssignID(PREFIX_TEACHERID);

        Course foundCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Staff foundStaff = (Staff) model.get(staffID, Constants.ENTITY_TYPE.STAFF);

        EdgeManager.unassignTeacherFromCourse(staffID, courseID);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                foundStaff.getName(), staffID.value,
                foundCourse.getName(), courseID.value));

    }

    /**
     * This performs all two main action to ensure successful Unassigning of Teacher from Course and that
     * the command can undo & redo successfully.
     * 1. Check to ensure that the targeted Teacher & Course exists.
     *
     * @param model the model.
     */
    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID staffID = this.assignDescriptor.getAssignID(PREFIX_TEACHERID);

        boolean courseExists = model.has(courseID, Constants.ENTITY_TYPE.COURSE);
        boolean staffExists = model.has(staffID, Constants.ENTITY_TYPE.STAFF);

        if (!courseExists) {
            throw new CommandException(MESSAGE_INVALID_COURSE_ID);
        } else if (!staffExists) {
            throw new CommandException(MESSAGE_INVALID_TEACHER_ID);
        } else {
            Course foundCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
            Staff foundStaff = (Staff) model.get(staffID, Constants.ENTITY_TYPE.STAFF);

            boolean assignedCourseContainsStaff = foundCourse.containsStaff(staffID);
            boolean assigningStaffContainsCourse = foundStaff.containsCourse(courseID);

            if (!assignedCourseContainsStaff) {
                throw new CommandException("This course doesn't have the specified teacher! :(");
            } else if (!assigningStaffContainsCourse) {
                throw new CommandException("The teacher isn't even assigned to this course! :(");
            }
        }
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new AssignTeacherToCourseCommand(this.assignDescriptor);
    }
}
