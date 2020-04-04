package seedu.address.logic.commands.commandUnassign;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.commandAssign.AssignStudentToCourseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.ID;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/** This class will be in charge of assigning stuff (e.g Assignments, teacher, etc) to a course. */
public class UnassignStudentFromCourseCommand extends UnassignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID";
    public static final String MESSAGE_INVALID_STUDENT_ID = "There is no such student that with ID";
    public static final String MESSAGE_SUCCESS = "Successfully unassigned student %s (%s) from course %s (%s)";

    private final AssignDescriptor assignDescriptor;
    private Set<Tag> ArrayList;

    public UnassignStudentFromCourseCommand(AssignDescriptor assignDescriptor) {
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
        ID studentID = this.assignDescriptor.getAssignID(PREFIX_STUDENTID);

        boolean courseExists = model.hasCourse(courseID);
        boolean StudentExists = model.hasStudent(studentID);

        if (!courseExists) {
            throw new CommandException(MESSAGE_INVALID_COURSE_ID);
        } else if (!StudentExists) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_ID);
        } else {
            Course assignedCourse = model.getCourse(courseID);
            Student assigningStudent = model.getStudent(studentID);

            boolean assignedCourseContainsStudent = assignedCourse.containsStudent(studentID);
            boolean assigningStudentContainsCourse = assigningStudent.containsCourse(courseID);

            if(!assignedCourseContainsStudent) {
                throw new CommandException("This course doesn't contain the specified student! :(");
            } else if(!assigningStudentContainsCourse) {
                throw new CommandException("The student isn't even assigned to this course! :(");
            } else {
                model.unassignStudentFromCourse(studentID, courseID);

                return new CommandResult(String.format(MESSAGE_SUCCESS,
                        assigningStudent.getName(), studentID.value,
                        assignedCourse.getName(), courseID.value));
            }
        }
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new AssignStudentToCourseCommand(this.assignDescriptor);
    }
}
