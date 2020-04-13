package seedu.address.logic.parser.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REMINDERS_EMPTY_DESCRIPTION;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.reminders.FindReminderCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.DatePredicate;
import seedu.address.model.reminder.DescriptionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindReminderCommand object
 */
public class FindReminderCommandParser implements Parser<FindReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindReminderCommand
     * and returns a FindReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_DESCRIPTION, PREFIX_REMINDER_DATE);

        boolean descriptionProvided = argMultimap.getValue(PREFIX_REMINDER_DESCRIPTION).isPresent();
        boolean dateProvided = argMultimap.getValue(PREFIX_REMINDER_DATE).isPresent();

        if (!arePrefixesUnique(argMultimap, PREFIX_REMINDER_DESCRIPTION, PREFIX_REMINDER_DATE)) {
            throw new ParseException(
                    String.format(MESSAGE_REPEATED_PREFIXES, FindReminderCommand.MESSAGE_USAGE));
        }

        if (descriptionProvided && dateProvided) {
            String trimmedArgs = argMultimap.getValue(PREFIX_REMINDER_DESCRIPTION).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(MESSAGE_REMINDERS_EMPTY_DESCRIPTION);
            }
            String[] descriptionKeywords = trimmedArgs.split("\\s+");
            return new FindReminderCommand(
                    new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionKeywords)),
                    new DatePredicate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_REMINDER_DATE).get())));
        }

        if (descriptionProvided) {
            String trimmedArgs = argMultimap.getValue(PREFIX_REMINDER_DESCRIPTION).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(MESSAGE_REMINDERS_EMPTY_DESCRIPTION);
            }
            String[] descriptionKeywords = trimmedArgs.split("\\s+");
            return new FindReminderCommand(
                    new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionKeywords)));
        }

        if (dateProvided) {
            return new FindReminderCommand(
                    new DatePredicate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_REMINDER_DATE).get())));
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindReminderCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if at least one of the prefixes is repeated in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesUnique(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(argumentMultimap::isRepeated).count() == 0;
    }
}
