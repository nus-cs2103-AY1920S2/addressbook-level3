package seedu.address.logic.parser.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_MINUTE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.reminders.SnoozeReminderCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SnoozeReminderCommand object
 */
public class SnoozeReminderCommandParser implements Parser<SnoozeReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SnoozeReminderCommand
     * and returns an SnoozeReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SnoozeReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_DAY, PREFIX_REMINDER_HOUR,
                        PREFIX_REMINDER_MINUTE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SnoozeReminderCommand.MESSAGE_USAGE), pe);
        }

        long dayToSnooze = 0;
        long hourToSnooze = 0;
        long minuteToSnooze = 0;

        if (!arePrefixesUnique(argMultimap, PREFIX_REMINDER_DAY, PREFIX_REMINDER_HOUR,
                PREFIX_REMINDER_MINUTE)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_PREFIXES,
                    SnoozeReminderCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_REMINDER_DAY).isPresent()) {
            dayToSnooze = ParserUtil.parseSnoozeDuration(argMultimap.getValue(PREFIX_REMINDER_DAY).get());
        }
        if (argMultimap.getValue(PREFIX_REMINDER_HOUR).isPresent()) {
            hourToSnooze = ParserUtil.parseSnoozeDuration(argMultimap.getValue(PREFIX_REMINDER_HOUR).get());
        }
        if (argMultimap.getValue(PREFIX_REMINDER_MINUTE).isPresent()) {
            minuteToSnooze = ParserUtil.parseSnoozeDuration(argMultimap.getValue(PREFIX_REMINDER_MINUTE).get());
        }

        if (dayToSnooze == 0 && hourToSnooze == 0 && minuteToSnooze == 0) {
            throw new ParseException(SnoozeReminderCommand.MESSAGE_NOT_EDITED);
        }

        return new SnoozeReminderCommand(index, dayToSnooze, hourToSnooze, minuteToSnooze);
    }

    /**
     * Returns true if at least one of the prefixes is repeated in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesUnique(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(argumentMultimap::isRepeated).count() == 0;
    }
}
