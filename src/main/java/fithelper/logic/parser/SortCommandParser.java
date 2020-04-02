package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SORTBY;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import fithelper.logic.commands.SortCommand;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.entry.SortBy;
import fithelper.model.entry.Type;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_SORTBY);
        if (!arePrefixesPresent(argMultimap, PREFIX_SORTBY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        SortBy sortBy = ParserUtil.parseSortBy(argMultimap.getValue(PREFIX_SORTBY).get());
        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE)) {
            return new SortCommand(null, sortBy);
        } else {
            Type findType = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
            return new SortCommand(findType, sortBy);
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
