package seedu.address.logic.parser.calendar;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.calendar.CalFindCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CalViewCommand object
 */
public class CalFindCommandParser implements Parser<CalFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CalFindCommand
     * and returns a CalFindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CalFindCommand parse(String args) throws ParseException {
        String trimmedArg = args.trim();
        if (!trimmedArg.equals("empty")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalFindCommand.MESSAGE_USAGE));
        }

        return new CalFindCommand();

    }
}
