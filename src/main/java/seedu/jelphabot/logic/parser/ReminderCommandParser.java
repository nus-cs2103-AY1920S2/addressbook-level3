//@@author yapdianhao
package seedu.jelphabot.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_REMIND_DAY;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_REMIND_HOUR;

import java.util.stream.Stream;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.ReminderCommand;
import seedu.jelphabot.logic.parser.exceptions.ParseException;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderDay;
import seedu.jelphabot.model.reminder.ReminderHour;

/**
 * Parses input arguments and returns a new ReminderCommand Object.
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReminderCommand
     * and returns a ReminderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(
                args,
                PREFIX_REMIND_DAY,
                PREFIX_REMIND_HOUR
            );

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE), pe);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_REMIND_DAY, PREFIX_REMIND_HOUR)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
        }
        ReminderDay reminderDay = ParserUtil.parseReminderDay(argMultimap.getValue(PREFIX_REMIND_DAY).get());
        ReminderHour reminderHour = ParserUtil.parseReminderHour(argMultimap.getValue(PREFIX_REMIND_HOUR).get());
        Reminder reminder = new Reminder(index, reminderDay, reminderHour);
        return new ReminderCommand(index, reminder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
