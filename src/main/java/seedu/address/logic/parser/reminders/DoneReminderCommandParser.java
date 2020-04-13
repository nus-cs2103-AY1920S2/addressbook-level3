package seedu.address.logic.parser.reminders;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.reminders.DoneReminderCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DoneReminderCommand object
 */
public class DoneReminderCommandParser implements Parser<DoneReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DoneReminderCommand
     * and returns a DoneReminderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DoneReminderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DoneReminderCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneReminderCommand.MESSAGE_USAGE), pe);
        }
    }

}
