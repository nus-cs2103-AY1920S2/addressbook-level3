package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_FILTERS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ESTHOURS_LIST;

import java.util.stream.Stream;

import seedu.address.logic.commands.ListAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.DeadlineComparator;
import seedu.address.model.assignment.TitleComparator;
import seedu.address.model.assignment.WorkloadComparator;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class ListAssignmentCommandParser implements Parser<ListAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListAssignmentCommand
     * and returns an ListAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE_LIST, PREFIX_ESTHOURS_LIST);

        if (arePrefixesPresent(argMultimap, PREFIX_DEADLINE_LIST, PREFIX_ESTHOURS_LIST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_FILTERS, ListAssignmentCommand.MESSAGE_USAGE));
        } else if (argMultimap.getValue(PREFIX_DEADLINE_LIST).isPresent()) {
            DeadlineComparator deadlineComparator = new DeadlineComparator();
            return new ListAssignmentCommand(deadlineComparator);
        } else if (argMultimap.getValue(PREFIX_ESTHOURS_LIST).isPresent()) {
            WorkloadComparator workloadComparator = new WorkloadComparator();
            return new ListAssignmentCommand(workloadComparator);
        } else {
            TitleComparator titleComparator = new TitleComparator();
            return new ListAssignmentCommand(titleComparator);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
