package seedu.address.logic.commands.commandAssign;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import seedu.address.logic.parser.Prefix;

public class AssignCommandFactory {
    public static AssignCommandBase getCommand(AssignDescriptor assignDescriptor) {

        Prefix[] prefixes = assignDescriptor.getType();
        if ( (prefixes[0].equals(PREFIX_TEACHERID) && prefixes[1].equals(PREFIX_COURSEID)) || (prefixes[1].equals(PREFIX_TEACHERID) && prefixes[0].equals(PREFIX_COURSEID)) ){
            if (AssignTeacherToCourseCommand.isValidDescriptor(assignDescriptor)) {
                return new AssignTeacherToCourseCommand(assignDescriptor);
            }
        }
        else if ( (prefixes[0].equals(PREFIX_COURSEID) && prefixes[1].equals(PREFIX_STUDENTID)) || (prefixes[1].equals(PREFIX_COURSEID) && prefixes[0].equals(PREFIX_STUDENTID)) ){
            if (AssignStudentToCourseCommand.isValidDescriptor(assignDescriptor)) {
                return new AssignStudentToCourseCommand(assignDescriptor);
            }
        }
        return new AssignCommandBase();
    }
}
