package seedu.nova.logic.parser.scparser.eventparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.sccommands.eventcommands.EventDeleteCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.CliSyntax;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EventDeleteCommand object.
 */
public class EventDeleteCommandParser implements Parser<EventDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EventDeleteCommand
     * and returns an EventDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventDeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_TIME, CliSyntax.PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_TIME, CliSyntax.PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventDeleteCommand.MESSAGE_USAGE));
        }

        String date = argMultimap.getValue(CliSyntax.PREFIX_TIME).get();
        String index = argMultimap.getValue(CliSyntax.PREFIX_INDEX).get();

        LocalDate localDate = ParserUtil.parseDate(date);
        Index i = ParserUtil.parseIndex(index);

        return new EventDeleteCommand(localDate, i);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
