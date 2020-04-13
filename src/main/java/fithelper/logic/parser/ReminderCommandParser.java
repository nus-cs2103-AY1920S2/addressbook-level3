package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.stream.Stream;

import fithelper.commons.core.LogsCenter;
import fithelper.logic.commands.ReminderCommand;
import fithelper.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {

    private static final Logger logger = LogsCenter.getLogger(ReminderCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        //if no date is specified, list out all entries
        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            return new ReminderCommand(null);
        }
        String dateStr = argMultimap.getValue(PREFIX_DATE).get().trim();
        if (dateStr.isEmpty() || !isValidDate(dateStr)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }
        return new ReminderCommand(dateStr);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks whether a date string is valid for LocalDate conversion.
     * @param dateStr
     * @return
     */
    public static boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateStr, formatter);
            logger.info(String.valueOf(date));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
