package seedu.address.logic.parser.reminders;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TIME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.reminders.EditReminderCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditReminderCommand object
 */
public class EditReminderCommandParser implements Parser<EditReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditReminderCommand
     * and returns an EditReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_DESCRIPTION, PREFIX_REMINDER_DATE,
                        PREFIX_REMINDER_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditReminderCommand.MESSAGE_USAGE), pe);
        }

        EditReminderCommand.EditReminderDescriptor editReminderDescriptor =
                new EditReminderCommand.EditReminderDescriptor();

        if (!arePrefixesUnique(argMultimap, PREFIX_REMINDER_DESCRIPTION, PREFIX_REMINDER_DATE,
                PREFIX_REMINDER_TIME)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_PREFIXES,
                    EditReminderCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_REMINDER_DESCRIPTION).isPresent()) {
            editReminderDescriptor.setDescription(ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_REMINDER_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_REMINDER_DATE).isPresent()) {
            editReminderDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_REMINDER_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_REMINDER_TIME).isPresent()) {
            editReminderDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_REMINDER_TIME).get()));
        }

        if (!editReminderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReminderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReminderCommand(index, editReminderDescriptor);
    }

    /**
     * Returns true if at least one of the prefixes is repeated in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesUnique(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(argumentMultimap::isRepeated).count() == 0;
    }
}
