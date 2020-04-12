package seedu.address.logic.commands.commandAssign;

import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandUnassign.UnassignTeacherFromCourseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.manager.EdgeManager;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelStaff.Staff;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

/**
 * This class will be in charge of assigning staff to a course.
 */
public class AssignTeacherToCourseCommand extends AssignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID";
    public static final String MESSAGE_INVALID_STAFF_ID = "There is no such staff that with ID";
    public static final String MESSAGE_NOT_TEACHER = "An admin staff can't teach a course, only teachers can!";

    public static final String MESSAGE_COURSE_ALREADY_CONTAINS_TEACHER = "Course already has an assigned teacher! Each course can only have 1 teacher.";
    public static final String MESSAGE_TEACHER_ALREADY_TEACHES_COURSE = "Teacher is assigned to the course already!";

    public static final String MESSAGE_SUCCESS = "Successfully assigned staff %s(%s) to course %s(%s)";

    private final AssignDescriptor assignDescriptor;

    public AssignTeacherToCourseCommand(AssignDescriptor assignDescriptor) {
        requireNonNull(assignDescriptor);

        this.assignDescriptor = assignDescriptor;
    }

    public static boolean isValidDescriptor(AssignDescriptor assignDescriptor) {
        Set<Prefix> allAssignEntities = assignDescriptor.getAllAssignKeys();
        return allAssignEntities.contains(PREFIX_COURSEID) && allAssignEntities.contains(PREFIX_TEACHERID);
    }


    /**
     * Executes the Assigning of Teacher to Course by calling EdgeManager and Progress Manager
     *
     * @param model
     * @return CommandResult of successful command execution
     * @throws CommandException
     */
    @Override
    protected CommandResult executeUndoableCommand(Model model) throws CommandException {
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID staffID = this.assignDescriptor.getAssignID(PREFIX_TEACHERID);

        Course foundCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Staff foundStaff = (Staff) model.get(staffID, Constants.ENTITY_TYPE.STAFF);

        EdgeManager.assignTeacherToCourse(staffID, courseID);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                foundStaff.getName().toString(), staffID.value,
                foundCourse.getName().toString(), courseID.value));
    }

    /**
     * Ensures that Teacher & Course exists and that they don't already contain each other.
     *
     * @param model
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
            throw new CommandException(MESSAGE_INVALID_STAFF_ID);
        } else {
            Course foundCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
            Staff foundStaff = (Staff) model.get(staffID, Constants.ENTITY_TYPE.STAFF);

            Boolean isTeacher = foundStaff.isTeacher();

            if (!isTeacher) {
                throw new CommandException(MESSAGE_NOT_TEACHER);
            } else {
                boolean assignedCourseContainsTeacher = foundCourse.hasTeacher();
                boolean assignedTeacherContainsCourse = foundStaff.containsCourse(courseID);
                if (assignedCourseContainsTeacher) {
                    throw new CommandException(MESSAGE_COURSE_ALREADY_CONTAINS_TEACHER);
                } else if (assignedTeacherContainsCourse) {
                    throw new CommandException(MESSAGE_TEACHER_ALREADY_TEACHES_COURSE);
                }
            }
        }
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new UnassignTeacherFromCourseCommand(this.assignDescriptor);
    }
}
