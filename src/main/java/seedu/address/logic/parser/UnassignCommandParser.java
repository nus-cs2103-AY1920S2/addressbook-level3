package seedu.address.logic.parser;

import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.commandUnassign.UnassignCommandBase;
import seedu.address.logic.commands.commandUnassign.UnassignCommandFactory;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class UnassignCommandParser implements Parser<UnassignCommandBase> {

    public static final String MESSAGE_USAGE =
            "unassign command must have 2 parameters only, 1 of which must be the CourseID!"
                    + "\n1. Unassigning a student from a course"
                    + "\n2. Unassigning a teacher from a course"
                    + "\n3. Unassigning an assignment from a course"
                    + "\nParameters: "
                    + "\n" + PREFIX_COURSEID + "COURSEID"
                    + "\n AND"
                    + "\n" + PREFIX_STUDENTID + "STUDENTID"
                    + " OR " + PREFIX_TEACHERID + "TEACHERID"
                    + " OR " + PREFIX_ASSIGNMENTID + "ASSIGNMENTID"
                    + "\n" + "Example: " + "unassign "
                    + PREFIX_COURSEID + "829 "
                    + PREFIX_STUDENTID + "33 ";

    /**
     * Parses the given arguments into context of AssignCommand (actually a class that inherits
     * from AssignCommand, decided by AssignCommandFactory)
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnassignCommandBase parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEACHERID, PREFIX_COURSEID, PREFIX_STUDENTID, PREFIX_ASSIGNMENTID);

        AssignDescriptor assignDescriptor = new AssignDescriptor();

        if (argMultimap.getValue(PREFIX_TEACHERID).isPresent()) {
            assignDescriptor.setAssignEntity(PREFIX_TEACHERID, ParserUtil.parseID(argMultimap.getValue(PREFIX_TEACHERID).get()));
        }

        if (argMultimap.getValue(PREFIX_COURSEID).isPresent()) {
            assignDescriptor.setAssignEntity(PREFIX_COURSEID, ParserUtil.parseID(argMultimap.getValue(PREFIX_COURSEID).get()));
        }

        if (argMultimap.getValue(PREFIX_STUDENTID).isPresent()) {
            assignDescriptor.setAssignEntity(PREFIX_STUDENTID, ParserUtil.parseID(argMultimap.getValue(PREFIX_STUDENTID).get()));
        }

        if (argMultimap.getValue(PREFIX_ASSIGNMENTID).isPresent()) {
            assignDescriptor.setAssignEntity(PREFIX_ASSIGNMENTID, ParserUtil.parseID(argMultimap.getValue(PREFIX_ASSIGNMENTID).get()));
        }

        Set<Prefix> allAssignPrefixes = assignDescriptor.getAllAssignKeys();

        // must have exactly two entities in an assignment
        if (allAssignPrefixes.size() != 2) {
            throw new ParseException(
                    String.format(MESSAGE_USAGE, "")
            );
        }

        return UnassignCommandFactory.getCommand(assignDescriptor);
    }
}
