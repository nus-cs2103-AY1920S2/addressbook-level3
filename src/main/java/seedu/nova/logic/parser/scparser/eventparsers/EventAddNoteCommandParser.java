package seedu.nova.logic.parser.scparser.eventparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddNoteCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.CliSyntax;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.Prefix;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EventAddNoteCommand object.
 */
public class EventAddNoteCommandParser implements Parser<EventAddNoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EventAddNoteCommand
     * and returns an EventAddNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventAddNoteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_DESC, CliSyntax.PREFIX_TIME, CliSyntax.PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_DESC, CliSyntax.PREFIX_TIME, CliSyntax.PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventAddNoteCommand.MESSAGE_USAGE));
        }

        String desc = argMultimap.getValue(CliSyntax.PREFIX_DESC).get();
        String date = argMultimap.getValue(CliSyntax.PREFIX_TIME).get();
        String index = argMultimap.getValue(CliSyntax.PREFIX_INDEX).get();

        LocalDate localDate = ParserUtil.parseDate(date);
        Index i = ParserUtil.parseIndex(index);

        return new EventAddNoteCommand(desc, localDate, i);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
