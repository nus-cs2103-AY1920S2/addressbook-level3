package seedu.address.logic.commands.commandUnassign;

import seedu.address.logic.commands.commandAssign.AssignAssignmentToCourseCommand;
import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.commandAssign.AssignStudentToCourseCommand;
import seedu.address.logic.commands.commandAssign.AssignTeacherToCourseCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.parser.CliSyntax.*;

public class UnassignCommandFactory {
    public static UnassignCommandBase getCommand(AssignDescriptor assignDescriptor) throws ParseException {

        final String UNASSIGNMENT_FAILURE_MESSAGE =
                "'unassign' command must have valid pair of IDs such as for: " +
                        "\n1. Unassigning a student from a course " +
                        PREFIX_COURSEID + " " + PREFIX_STUDENTID +
                        "\n2. Unassigning a teacher from a course " +
                        PREFIX_COURSEID + " " + PREFIX_TEACHERID +
                        "\n3. Unassigning an assignment from a course " +
                        PREFIX_COURSEID + " " + PREFIX_ASSIGNMENTID;


        Prefix[] prefixes = assignDescriptor.getType();

        UnassignCommandBase outputCommand = null;
        if (AssignTeacherToCourseCommand.isValidDescriptor(assignDescriptor)) {
            outputCommand = new UnassignTeacherFromCourseCommand(assignDescriptor);
        } else if (AssignStudentToCourseCommand.isValidDescriptor(assignDescriptor)) {
            outputCommand = new UnassignStudentFromCourseCommand(assignDescriptor);
        } else if (AssignAssignmentToCourseCommand.isValidDescriptor(assignDescriptor)) {
            outputCommand = new UnassignAssignmentFromCourseCommand(assignDescriptor);
        } else {
            // Thrown when there is no valid assignment such as Teachers -> Students
            throw new ParseException(UNASSIGNMENT_FAILURE_MESSAGE);
        }

        return outputCommand;
    }
}
