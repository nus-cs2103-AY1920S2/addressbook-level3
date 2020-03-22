package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import java.util.Set;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import seedu.address.logic.commands.commandAssign.AssignCommandFactory;
import seedu.address.logic.commands.commandAssign.AssignDescriptor;
import seedu.address.logic.commands.commandAssign.AssignCommandBase;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class AssignCommandParser implements Parser<AssignCommandBase> {

    /**
     * Parses the given arguments into context of AssignCommand (actually a class that inherits
     * from AssignCommand, decided by AssignCommandFactory)
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommandBase parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEACHERID, PREFIX_COURSEID, PREFIX_STUDENTID);

        // TODO: validate based on optional params as well.

        AssignDescriptor assignDescriptor = new AssignDescriptor();
        if (argMultimap.getValue(PREFIX_TEACHERID).isPresent()) {
            assignDescriptor
                    .setAssignEntity(PREFIX_TEACHERID, ParserUtil.parseID(argMultimap.getValue(PREFIX_TEACHERID).get()));
        }

        if (argMultimap.getValue(PREFIX_COURSEID).isPresent()) {
            assignDescriptor.setAssignEntity(PREFIX_COURSEID, ParserUtil.parseID(argMultimap.getValue(PREFIX_COURSEID).get()));
        }

        if (argMultimap.getValue(PREFIX_STUDENTID).isPresent()) {
            assignDescriptor.setAssignEntity(PREFIX_STUDENTID, ParserUtil.parseID(argMultimap.getValue(PREFIX_STUDENTID).get()));
        }

        Set<Prefix> allAssignPrefixes = assignDescriptor.getAllAssignKeys();
        // must have exactly two entities in an assignment
        if (allAssignPrefixes.size() != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, "")
            );
        }

        return AssignCommandFactory.getCommand(assignDescriptor);
    }
}
