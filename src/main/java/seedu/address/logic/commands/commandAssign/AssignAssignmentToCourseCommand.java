package seedu.address.logic.commands.commandAssign;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandUnassign.UnassignAssignmentFromCourseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.manager.EdgeManager;
import seedu.address.manager.ProgressManager;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.person.ID;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import java.util.logging.Logger;

/** This class will be in charge of assigning stuff (e.g Assignments, teacher, etc) to a course. */
public class AssignAssignmentToCourseCommand extends AssignCommandBase {
    private static final Logger logger = LogsCenter.getLogger(AssignAssignmentToCourseCommand.class);

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such Course that with ID";
    public static final String MESSAGE_INVALID_ASSIGNMENT_ID = "There is no such Assignment that with ID";
    public static final String MESSAGE_INVALID_ASSIGNMENT_ALREADY_ASSIGNED = "The assignment has already been assigned already! Each assignment can only be assigned to one course.";
    public static final String MESSAGE_SUCCESS = "Successfully assigned Assignment %s (%s) to Course %s (%s)";

    private final AssignDescriptor assignDescriptor;

    public AssignAssignmentToCourseCommand(AssignDescriptor assignDescriptor) {
        requireNonNull(assignDescriptor);

        this.assignDescriptor = assignDescriptor;
    }

    public static boolean isValidDescriptor(AssignDescriptor assignDescriptor) {
        Set<Prefix> allAssignEntities = assignDescriptor.getAllAssignKeys();
        return allAssignEntities.contains(PREFIX_COURSEID) && allAssignEntities.contains(PREFIX_ASSIGNMENTID);
    }

    @Override
    protected CommandResult executeUndoableCommand(Model model) throws CommandException {
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

            if(assigningAssignmentContainsCourse) {
                throw new CommandException(MESSAGE_INVALID_ASSIGNMENT_ALREADY_ASSIGNED);
            } else {
                EdgeManager.assignAssignmentToCourse(assignmentID, courseID);
                ProgressManager.addOneAssignmentToAllStudents(courseID, assignmentID);

                return new CommandResult(String.format(MESSAGE_SUCCESS,
                        assigningAssignment.getName(), assignmentID.value,
                        assignedCourse.getName(), courseID.value));
            }
        }
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new UnassignAssignmentFromCourseCommand(this.assignDescriptor);
    }
}
