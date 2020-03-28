package seedu.address.logic.commands.commandAssign;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignCommandFactory {
    public static AssignCommandBase getCommand(AssignDescriptor assignDescriptor) throws ParseException {
        final String ASSIGNMENT_FAILURE_MESSAGE =
                "assign command must have valid pair of IDs such as for: " +
                "\n1. Assigning a student to a course " +
                PREFIX_COURSEID + " " + PREFIX_STUDENTID +
                "\n2. Assigning a teacher to a course " +
                PREFIX_COURSEID + " " + PREFIX_TEACHERID;

        Prefix[] prefixes = assignDescriptor.getType();

        AssignCommandBase outputCommand = null;

        if ( (prefixes[0].equals(PREFIX_TEACHERID) && prefixes[1].equals(PREFIX_COURSEID)) || (prefixes[1].equals(PREFIX_TEACHERID) && prefixes[0].equals(PREFIX_COURSEID)) ){
            if (AssignTeacherToCourseCommand.isValidDescriptor(assignDescriptor)) {
                outputCommand = new AssignTeacherToCourseCommand(assignDescriptor);
            }
        }
        else if ( (prefixes[0].equals(PREFIX_COURSEID) && prefixes[1].equals(PREFIX_STUDENTID)) || (prefixes[1].equals(PREFIX_COURSEID) && prefixes[0].equals(PREFIX_STUDENTID)) ){
            if (AssignStudentToCourseCommand.isValidDescriptor(assignDescriptor)) {
                outputCommand = new AssignStudentToCourseCommand(assignDescriptor);
            }
        }
        else {
            // Thrown when there is no valid assignment such as Teachers -> Students
            throw new ParseException(ASSIGNMENT_FAILURE_MESSAGE);
        }

        return outputCommand;
    }
}
