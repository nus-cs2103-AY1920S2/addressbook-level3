package seedu.address.logic.commands.commandUnassign;

import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.commandAssign.AssignStudentToCourseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.manager.EdgeManager;
import seedu.address.manager.ProgressManager;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.modelStudent.Student;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

/**
 * This class will be in charge of assigning stuff (e.g Assignments, teacher, etc) to a course.
 */
public class UnassignStudentFromCourseCommand extends UnassignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID";
    public static final String MESSAGE_INVALID_COURSE_NO_SUCH_STUDENT = "This course doesn't contain the specified student! :(";
    public static final String MESSAGE_INVALID_STUDENT_ID = "There is no such student that with ID";
    public static final String MESSAGE_INVALID_STUDENT_NOT_ASSIGNED_TO_COURSE = "The student isn't even assigned to this course! :(";
    public static final String MESSAGE_SUCCESS = "Successfully unassigned student %s (%s) from course %s (%s)";

    private final AssignDescriptor assignDescriptor;
    private Set<Progress> undoProgresses;

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
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID studentID = this.assignDescriptor.getAssignID(PREFIX_STUDENTID);

        Course assignedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Student assigningStudent = (Student) model.get(studentID, Constants.ENTITY_TYPE.STUDENT);

        EdgeManager.unassignStudentFromCourse(studentID, courseID);
        ProgressManager.removeAllProgressesFromOneStudent(courseID, studentID);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                assigningStudent.getName(), studentID.value,
                assignedCourse.getName(), courseID.value));
    }

    /**
     * This performs all two main action to ensure successful Unassigning of Student from Course and that
     * the command can undo & redo successfully.
     * 1. Check to ensure that the targeted Student & Assignment exists and that
     * 2. Prepare to add the removed 'Progress' objects back into the Progress AddressBook if the user decides to undo
     *
     * @param model
     */
    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID studentID = this.assignDescriptor.getAssignID(PREFIX_STUDENTID);

        boolean courseExists = model.has(courseID, Constants.ENTITY_TYPE.COURSE);
        boolean studentExists = model.has(studentID, Constants.ENTITY_TYPE.STUDENT);

        if (!courseExists) {
            throw new CommandException(MESSAGE_INVALID_COURSE_ID);
        } else if (!studentExists) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_ID);
        } else {
            Course assignedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
            Student assigningStudent = (Student) model.get(studentID, Constants.ENTITY_TYPE.STUDENT);

            boolean assignedCourseContainsStudent = assignedCourse.containsStudent(studentID);
            boolean assigningStudentContainsCourse = assigningStudent.containsCourse(courseID);

            if (!assignedCourseContainsStudent) {
                throw new CommandException(MESSAGE_INVALID_COURSE_NO_SUCH_STUDENT);
            } else if (!assigningStudentContainsCourse) {
                throw new CommandException(MESSAGE_INVALID_STUDENT_NOT_ASSIGNED_TO_COURSE);
            } else {
                Set<Progress> undoProgress = ProgressManager.getAllProgressesForOneStudent(courseID, studentID);
                this.undoProgresses = undoProgress;
            }
        }
    }


    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new AssignStudentToCourseCommand(this.assignDescriptor, this.undoProgresses);
    }
}
