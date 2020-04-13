package seedu.address.logic.parser.reminders;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.reminders.AddReminderCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.Reminder;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddReminderCommand
     * and returns an AddReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_DESCRIPTION,
                        PREFIX_REMINDER_DATE, PREFIX_REMINDER_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMINDER_DESCRIPTION,
                PREFIX_REMINDER_DATE, PREFIX_REMINDER_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddReminderCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesUnique(argMultimap, PREFIX_REMINDER_DESCRIPTION,
                PREFIX_REMINDER_DATE, PREFIX_REMINDER_TIME)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_PREFIXES,
                    AddReminderCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(
                PREFIX_REMINDER_DESCRIPTION).get());
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(
                PREFIX_REMINDER_DATE).get());
        LocalTime time = ParserUtil.parseTime(argMultimap.getValue(
                PREFIX_REMINDER_TIME).get());

        Reminder reminder = new Reminder(description, date, time, false);

        return new AddReminderCommand(reminder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if at least one of the prefixes is repeated in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesUnique(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(argumentMultimap::isRepeated).count() == 0;
    }
}
