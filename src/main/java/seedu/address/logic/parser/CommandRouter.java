package seedu.address.logic.parser;

import seedu.address.logic.commands.AppCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.commands.FilterTimestampCommand;
import seedu.address.logic.commands.FilterUserIDCommand;
import seedu.address.logic.commands.FilterUserPairsCommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

public class CommandRouter {
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public AppCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, this.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case FilterTimestampCommand.COMMAND_WORD:
            return new FilterTimestampCommand().validate(arguments);

        case FilterUserIDCommand.COMMAND_WORD:
            return new FilterUserIDCommand().validate(arguments);

        case FilterUserPairsCommand.COMMAND_WORD:
            return new FilterUserPairsCommand().validate(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
