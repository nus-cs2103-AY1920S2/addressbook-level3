package seedu.eylah.diettracker.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.diettracker.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.eylah.diettracker.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.eylah.diettracker.logic.parser.CliSyntax.PREFIX_SINGLE;

import java.util.stream.Stream;

import seedu.eylah.diettracker.logic.commands.ListCommand;
import seedu.eylah.diettracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALL, PREFIX_SINGLE, PREFIX_DAYS);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        int numDays = ParserUtil.parseDays(argMultimap.getValue(PREFIX_DAYS).get());
        String mode = "-d";
        if (arePrefixesPresent(argMultimap, PREFIX_ALL)) {
            mode = "-f";
        } else if (arePrefixesPresent(argMultimap, PREFIX_SINGLE)) {
            mode = "-d";
        } else if (arePrefixesPresent(argMultimap, PREFIX_DAYS)) {
            mode = "-t";
        } else {

        }

        if (mode.equals("-t")) {
            return new ListCommand(mode, numDays);
        } else {
            return new ListCommand(mode);
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
