package seedu.address.logic.commands.commandUnassign;

import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAssign.AssignAssignmentToCourseCommand;
import seedu.address.logic.commands.commandAssign.AssignCommandBase;
import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.manager.EdgeManager;
import seedu.address.manager.ProgressManager;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.person.ID;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;

/** This class will be in charge of assigning stuff (e.g Assignments, teacher, etc) to a course. */
public class UnassignAssignmentFromCourseCommand extends UnassignCommandBase {

    public static final String MESSAGE_INVALID_COURSE_ID = "There is no such Course that with ID";
    public static final String MESSAGE_INVALID_ASSIGNMENT_ID = "There is no such Assignment that with ID";
    public static final String MESSAGE_SUCCESS = "Successfully assigned Assignment %s (%s) to Course %s (%s)";

    private final AssignDescriptor assignDescriptor;

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

            if(!assignedCourseContainsAssignment) {
                throw new CommandException("This course doesn't have the assignment assigned to it! :(");
            } else if(!assigningAssignmentContainsCourse) {
                throw new CommandException("The assignment isn't assigned to this course! :(");
            } else {
                EdgeManager.unassignAssignmentFromCourse(assignmentID, courseID);
                ProgressManager.removeOneAssignmentsFromAllStudents(courseID, assignmentID);


                return new CommandResult(String.format(MESSAGE_SUCCESS,
                        assigningAssignment.getName(), assignmentID.value,
                        assignedCourse.getName(), courseID.value));
            }
        }
    }

    @Override
    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new AssignAssignmentToCourseCommand(this.assignDescriptor);
    }
}
