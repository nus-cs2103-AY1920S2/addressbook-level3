package seedu.address.logic.parser.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MULTIPLE_SAME_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.product.PlotSalesCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.DateTime;

/**
 * Parses input arguments and creates a new PlotProductSalesCommand object
 */
public class PlotSalesCommandParser implements Parser<PlotSalesCommand> {

    private static final int DEFAULT_LENGTH = 7;
    private static final DateTime DEFAULT_START_DATE =
            new DateTime(DateTime.DEFAULT_VALUE.minusDays(DEFAULT_LENGTH));

    /**
     * Parses the given {@code String} of arguments in the context of the PlotProductSalesCommand
     * and returns a PlotProductSalesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PlotSalesCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATE, PREFIX_END_DATE);
        Index index;

        if (anyPrefixesDuplicate(argMultimap, PREFIX_START_DATE, PREFIX_END_DATE)) {
            throw new ParseException(String.format(MESSAGE_MULTIPLE_SAME_PREFIX,
                    PlotSalesCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlotSalesCommand.MESSAGE_USAGE), pe);
        }

        DateTime startDateTime = getStartDateTime(argMultimap);
        DateTime endDateTime = getEndDateTime(argMultimap, startDateTime);

        return new PlotSalesCommand(index, startDateTime, endDateTime);
    }

    private DateTime getStartDateTime(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            return ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATE).get());
        } else {
            return DEFAULT_START_DATE;
        }
    }

    private DateTime getEndDateTime(ArgumentMultimap argMultimap, DateTime startDateTime)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            return ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATE).get());
        } else {
            return new DateTime(startDateTime.value.plusDays(DEFAULT_LENGTH));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesDuplicate(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.hasDuplicateValues(prefix));
    }
}

