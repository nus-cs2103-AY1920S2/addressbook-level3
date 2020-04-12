package seedu.address.logic.commands.commandUnassign;

import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAssign.AssignAssignmentToCourseCommand;
import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.manager.EdgeManager;
import seedu.address.manager.ProgressManager;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelProgress.Progress;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;

/**
 * This class will be in charge of assigning stuff (e.g Assignments, teacher, etc) to a course.
 */
public class UnassignAssignmentFromCourseCommand extends UnassignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such Course that with ID";
    public static final String MESSAGE_INVALID_COURSE_NO_SUCH_ASSIGNMENT = "This course doesn't have the assignment assigned to it! :(";
    public static final String MESSAGE_INVALID_ASSIGNMENT_ID = "There is no such Assignment that with ID";
    public static final String MESSAGE_INVALID_ASSIGNMENT_TO_COURSE = "The assignment isn't assigned to this course! :(";
    public static final String MESSAGE_SUCCESS = "Successfully unassigned Assignment %s (%s) FROM Course %s (%s)";

    private final AssignDescriptor assignDescriptor;
    private Set<Progress> undoProgresses;

    public UnassignAssignmentFromCourseCommand(AssignDescriptor assignDescriptor) {
        requireNonNull(assignDescriptor);
        this.assignDescriptor = assignDescriptor;
    }

    public static boolean isValidDescriptor(AssignDescriptor assignDescriptor) {
        Set<Prefix> allAssignEntities = assignDescriptor.getAllAssignKeys();
        return allAssignEntities.contains(PREFIX_COURSEID) && allAssignEntities.contains(PREFIX_ASSIGNMENTID);
    }

    @Override
    protected CommandResult executeUndoableCommand(Model model) throws CommandException {
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID assignmentID = this.assignDescriptor.getAssignID(PREFIX_ASSIGNMENTID);

        Course assignedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
        Assignment assigningAssignment = (Assignment) model.get(assignmentID, Constants.ENTITY_TYPE.ASSIGNMENT);

        EdgeManager.unassignAssignmentFromCourse(assignmentID, courseID);
        ProgressManager.removeOneProgressFromAllStudents(courseID, assignmentID);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                assigningAssignment.getName(), assignmentID.value,
                assignedCourse.getName(), courseID.value));
    }

    /**
     * This performs all two main action to ensure successful Unassigning of Assignment from Course and that
     * the command can undo & redo successfully.
     * 1. Check to ensure that the targeted Course & Assignment exists and that
     * 2. Prepare to add the removed 'Progress' objects back into the Progress AddressBook if the user decides to undo
     *
     * @param model
     */
    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        // Check whether both IDs even exists
        ID courseID = this.assignDescriptor.getAssignID(PREFIX_COURSEID);
        ID assignmentID = this.assignDescriptor.getAssignID(PREFIX_ASSIGNMENTID);

        boolean courseExists = model.has(courseID, Constants.ENTITY_TYPE.COURSE);
        boolean assignmentExists = model.has(assignmentID, Constants.ENTITY_TYPE.ASSIGNMENT);

        if (!courseExists) {
            throw new CommandException(MESSAGE_INVALID_COURSE_ID);
        } else if (!assignmentExists) {
            throw new CommandException(MESSAGE_INVALID_ASSIGNMENT_ID);
        } else {
            Course assignedCourse = (Course) model.get(courseID, Constants.ENTITY_TYPE.COURSE);
            Assignment assigningAssignment = (Assignment) model.get(assignmentID, Constants.ENTITY_TYPE.ASSIGNMENT);

            boolean assignedCourseContainsAssignment = assignedCourse.containsAssignment(assignmentID);
            boolean assigningAssignmentContainsCourse = assigningAssignment.isAssignedToCourse();

            if (!assignedCourseContainsAssignment) {
                throw new CommandException(MESSAGE_INVALID_COURSE_NO_SUCH_ASSIGNMENT);
            } else if (!assigningAssignmentContainsCourse) {
                throw new CommandException(MESSAGE_INVALID_ASSIGNMENT_TO_COURSE);
            } else {
                Set<Progress> undoProgress = ProgressManager.getOneProgressFromAllStudents(courseID, assignmentID);
                this.undoProgresses = undoProgress;
            }
        }
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new AssignAssignmentToCourseCommand(this.assignDescriptor, undoProgresses);
    }
}
