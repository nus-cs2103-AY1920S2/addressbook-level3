package seedu.address.logic.parser.parserUndone;

import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.commandDone.DoneCommandBase;
import seedu.address.logic.commands.commandDone.DoneCommandFactory;
import seedu.address.logic.commands.commandUndone.UndoneCommandBase;
import seedu.address.logic.commands.commandUndone.UndoneCommandFactory;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class UndoneCommandParser implements Parser<UndoneCommandBase> {

    public static final String MESSAGE_USAGE =
        "undone command must at least have an AssignmentID"
        + "\nto be able to mark all students who are taking a particular assignment in a course as done."
        + "\nNote that all assignments can only assigned to 1 course!"
        + "\nA studentID can be o"
        + "\n3. Assigning an assignment to a course"
        + "\nParameters: "
        + "\n" + PREFIX_COURSEID + "COURSEID"
        + "\n AND"
        + "\n" + PREFIX_STUDENTID + "STUDENTID"
        + " OR " + PREFIX_TEACHERID + "TEACHERID"
        + " OR " + PREFIX_ASSIGNMENTID + "ASSIGNMENTID"
        + "\n" + "Example: "  + "assign "
        + PREFIX_COURSEID + "829 "
        + PREFIX_STUDENTID + "33 ";

    /**
     * Parses the given arguments into context of AssignCommand (actually a class that inherits
     * from AssignCommand, decided by AssignCommandFactory)
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoneCommandBase parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENTID, PREFIX_ASSIGNMENTID);

        AssignDescriptor assignDescriptor = new AssignDescriptor();

        if (argMultimap.getValue(PREFIX_STUDENTID).isPresent()) {
            assignDescriptor.setAssignEntity(PREFIX_STUDENTID, ParserUtil.parseID(argMultimap.getValue(PREFIX_STUDENTID).get()));
        }

        if (argMultimap.getValue(PREFIX_ASSIGNMENTID).isPresent()) {
            assignDescriptor.setAssignEntity(PREFIX_ASSIGNMENTID, ParserUtil.parseID(argMultimap.getValue(PREFIX_ASSIGNMENTID).get()));
        }

        return UndoneCommandFactory.getCommand(assignDescriptor);
    }
}
