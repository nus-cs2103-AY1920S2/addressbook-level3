package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_FILTERS;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ListAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.assignment.*;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class ListAssignmentCommandParser implements Parser<ListAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListAssignmentCommand parse(String args, Model model) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE_LIST, PREFIX_ESTHOURS_LIST);

        if (arePrefixesPresent(argMultimap, PREFIX_DEADLINE_LIST, PREFIX_ESTHOURS_LIST)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_FILTERS, ListAssignmentCommand.MESSAGE_USAGE));
        } else if (arePrefixesPresent(argMultimap, PREFIX_DEADLINE_LIST)) {
            DeadlineComparator deadlineComparator = new DeadlineComparator();
            return new ListAssignmentCommand(deadlineComparator);
        } else if (arePrefixesPresent(argMultimap, PREFIX_ESTHOURS_LIST)) {
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
