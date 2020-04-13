package seedu.eylah.diettracker.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.eylah.commons.logic.parser.ArgumentMultimap;
import seedu.eylah.commons.logic.parser.ArgumentTokenizer;
import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.Prefix;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.commands.MetricsCommand;

/**
 * Parses input arguments and creates a new MetricsCommand object
 */
public class MetricsCommandParser implements Parser<MetricsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MetricsCommand
     * and returns an MetricsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MetricsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MetricsCommand.MESSAGE_USAGE));
        }

        return new MetricsCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
