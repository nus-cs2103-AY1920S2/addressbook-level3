package seedu.address.logic.commands.commandAssign;

import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.parser.CliSyntax.*;

public class AssignCommandFactory {
    public static AssignCommandBase getCommand(AssignDescriptor assignDescriptor) throws ParseException {

        final String ASSIGNMENT_FAILURE_MESSAGE =
                "assign command must have valid pair of IDs such as for: " +
                        "\n1. Assigning a student to a course " +
                        PREFIX_COURSEID + " " + PREFIX_STUDENTID +
                        "\n2. Assigning a teacher to a course " +
                        PREFIX_COURSEID + " " + PREFIX_TEACHERID +
                        "\n3. Assigning an assignment to a course " +
                        PREFIX_COURSEID + " " + PREFIX_ASSIGNMENTID;

        AssignCommandBase outputCommand = null;

        if (AssignTeacherToCourseCommand.isValidDescriptor(assignDescriptor)) {
            outputCommand = new AssignTeacherToCourseCommand(assignDescriptor);
        } else if (AssignStudentToCourseCommand.isValidDescriptor(assignDescriptor)) {
            outputCommand = new AssignStudentToCourseCommand(assignDescriptor);
        } else if (AssignAssignmentToCourseCommand.isValidDescriptor(assignDescriptor)) {
            outputCommand = new AssignAssignmentToCourseCommand(assignDescriptor);
        } else {
            throw new ParseException(ASSIGNMENT_FAILURE_MESSAGE);
        }

        return outputCommand;
    }
}
