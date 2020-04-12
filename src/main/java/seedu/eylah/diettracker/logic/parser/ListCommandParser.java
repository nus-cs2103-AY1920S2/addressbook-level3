package seedu.eylah.diettracker.logic.parser;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_ALL;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_DAYS;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.eylah.commons.logic.parser.ArgumentMultimap;
import seedu.eylah.commons.logic.parser.ArgumentTokenizer;
import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.Prefix;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.commands.ListCommand;
import seedu.eylah.diettracker.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_ALL, PREFIX_DAYS, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        String mode = "";
        if (arePrefixesPresent(argMultimap, PREFIX_ALL)) {
            mode = "-a";
        } else if (arePrefixesPresent(argMultimap, PREFIX_DAYS)) {
            mode = "-d";
        } else if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            mode = "-t";
        }

        if (mode.equals("-d")) {
            int numDays;
            try {
                System.out.println("VALUE: " + argMultimap.getValue(PREFIX_DAYS).get());
                if (argMultimap.getValue(PREFIX_DAYS).get().length() > 6) {
                    throw new ParseException("Input days is invalid.");
                }
                numDays = ParserUtil.parseDays(argMultimap.getValue(PREFIX_DAYS).get());
                if (numDays < 1) {
                    throw new ParseException("Input days is invalid.");
                }
            } catch (ParseException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE_DAYS));
            }
            return new ListCommand(mode, numDays);
        } else if (mode.equals("-t")) {
            Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return new ListCommand(mode, tag);
        } else { // "" and "-a"
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
