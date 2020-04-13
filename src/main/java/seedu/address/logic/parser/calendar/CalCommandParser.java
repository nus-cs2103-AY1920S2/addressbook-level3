package seedu.address.logic.parser.calendar;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_CALENDAR_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.calendar.CalCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CalCommand object.
 */
public class CalCommandParser implements Parser<CalCommand> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_CAL_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    @Override
    public CalCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_CAL_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_CALENDAR_COMMAND, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case Command.COMMAND_WORD_VIEW:
            return new CalViewCommandParser().parse(arguments);
        case Command.COMMAND_WORD_FIND:
            return new CalFindCommandParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_CALENDAR_COMMAND, HelpCommand.MESSAGE_USAGE));
        }
    }
}
