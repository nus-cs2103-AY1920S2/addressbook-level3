package seedu.address.logic.commands.commandAssign;

import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandUnassign.UnassignStudentFromCourseCommand;
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
 * This class will be in charge of assigning Student to a Course.
 */
public class AssignStudentToCourseCommand extends AssignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such course that with ID!";
    public static final String MESSAGE_INVALID_STUDENT_ID = "There is no such student that with ID!";
    public static final String MESSAGE_COURSE_ALREADY_CONTAINS_STUDENT = "Course already has that student assigned to it!";
    public static final String MESSAGE_STUDENT_ALREADY_COURSE = "Student is assigned to the course already!";

    public static final String MESSAGE_SUCCESS = "Successfully assigned student %s (%s) to course %s (%s)";

    private final AssignDescriptor assignDescriptor;
    private Set<Progress> undoProgresses;

    public AssignStudentToCourseCommand(AssignDescriptor assignDescriptor) {
        requireNonNull(assignDescriptor);

        this.assignDescriptor = assignDescriptor;
    }

    public AssignStudentToCourseCommand(AssignDescriptor assignDescriptor, Set<Progress> undoProgresses) {
        requireNonNull(assignDescriptor);
        this.assignDescriptor = assignDescriptor;
        this.undoProgresses = undoProgresses;
    }

    public static boolean isValidDescriptor(AssignDescriptor assignDescriptor) {
        Set<Prefix> allAssignEntities = assignDescriptor.getAllAssignKeys();
        return allAssignEntities.contains(PREFIX_COURSEID) && allAssignEntities.contains(PREFIX_STUDENTID);
    }

    /**
     * Executes the AssignStudentToCourse command by calling EdgeManager and Progress Manager
     *
     * @param model
     * @return A successful commandResult
     * @throws CommandException
     */
    @Override
    protected CommandResult executeUndoableCommand(Model model) throws CommandException {
        // Check whether both IDs even exists
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID studentID = this.assignDescriptor.getAssignID(PREFIX_STUDENTID);

        Course assignedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Student assigningStudent = (Student) model.get(studentID, Constants.ENTITY_TYPE.STUDENT);

        if (this.undoProgresses != null) {
            ProgressManager.addUndoProgress(this.undoProgresses);
        } else {
            ProgressManager.addAllProgressesToOneStudent(courseID, studentID);
        }

        EdgeManager.assignStudentToCourse(studentID, courseID);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                assigningStudent.getName(), studentID.value,
                assignedCourse.getName(), courseID.value));
    }

    /**
     * This step is performed to ensure that all specified ModelObjects exists and that they do not already contain
     * the modelobjects that we want to assign to it.
     *
     * @param model
     */
    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        // Check whether both IDs even exists
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

            if (assignedCourseContainsStudent) {
                throw new CommandException(MESSAGE_COURSE_ALREADY_CONTAINS_STUDENT);
            } else if (assigningStudentContainsCourse) {
                throw new CommandException(MESSAGE_STUDENT_ALREADY_COURSE);
            }
        }
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new UnassignStudentFromCourseCommand(this.assignDescriptor);
    }
}
