package seedu.address.logic.commands.commandAssign;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COURSES;

import java.util.Set;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandUnassign.UnassignTeacherFromCourseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.manager.EdgeManager;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.person.ID;

/** This class will be in charge of assigning stuff (e.g students, staff, etc) to a course. */
public class AssignTeacherToCourseCommand extends AssignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID";
    public static final String MESSAGE_INVALID_STAFF_ID = "There is no such staff that with ID";
    public static final String MESSAGE_NOT_TEACHER = "An admin staff can't teach a course, only teachers can!";

    public static final String MESSAGE_COURSE_ALREADY_CONTAINS_TEACHER = "Course already has an assigned teacher!";
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

    @Override
    protected CommandResult executeUndoableCommand(Model model) throws CommandException {
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID staffID = this.assignDescriptor.getAssignID(PREFIX_TEACHERID);

        boolean courseExists = model.hasCourse(courseID);
        boolean staffExists = model.hasStaff(staffID);

        if (!courseExists) {
            throw new CommandException(MESSAGE_INVALID_COURSE_ID);
        } else if (!staffExists) {
            throw new CommandException(MESSAGE_INVALID_STAFF_ID);
        } else {
            Staff foundStaff = model.getStaff(staffID);
            Course foundCourse = model.getCourse(courseID);
            Boolean isTeacher = foundStaff.isTeacher();

            if (!isTeacher) {
                throw new CommandException(MESSAGE_NOT_TEACHER);
            } else {
                boolean assignedCourseContainsTeacher = foundCourse.hasTeacher();

                if (assignedCourseContainsTeacher) {
                    throw new CommandException(MESSAGE_COURSE_ALREADY_CONTAINS_TEACHER);
                } else {
                    EdgeManager.assignTeacherToCourse(staffID, courseID);
                    return new CommandResult(String.format(MESSAGE_SUCCESS,
                            foundStaff.getName().toString(), staffID.value,
                            foundCourse.getName().toString(), courseID.value));
                }
            }
        }
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new UnassignTeacherFromCourseCommand(this.assignDescriptor);
    }
}
