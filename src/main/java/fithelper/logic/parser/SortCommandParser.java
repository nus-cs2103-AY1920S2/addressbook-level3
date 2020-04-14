package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SORT_BY;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SORT_ORDER;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;
import java.util.stream.Stream;

import fithelper.commons.core.LogsCenter;
import fithelper.logic.commands.SortCommand;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.entry.SortBy;
import fithelper.model.entry.Type;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final Logger logger = LogsCenter.getLogger(SortCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("input parameters:" + args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_SORT_BY, PREFIX_SORT_ORDER);
        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_BY)) {
            logger.info("throwing wrong format exception");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        SortBy sortBy = ParserUtil.parseSortBy(argMultimap.getValue(PREFIX_SORT_BY).get());
        Type findType = null;
        if (arePrefixesPresent(argMultimap, PREFIX_TYPE)) {
            findType = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_ORDER)) {
            return new SortCommand(findType, sortBy, false);
        } else {
            boolean isAscendingSort = ParserUtil.parseSortOrder(argMultimap.getValue(PREFIX_SORT_ORDER).get());
            return new SortCommand(findType, sortBy, isAscendingSort);
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
