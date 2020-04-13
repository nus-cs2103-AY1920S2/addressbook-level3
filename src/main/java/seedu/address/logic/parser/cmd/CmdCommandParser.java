package seedu.address.logic.parser.cmd;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_CMD_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.cmd.CmdCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses cmd commands.
 * This group of command returns information on other command groups.
 */
public class CmdCommandParser implements Parser<CmdCommand> {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_CMD_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    @Override
    public CmdCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_CMD_COMMAND_FORMAT.matcher(userInput);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_CMD_COMMAND, CmdCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case Command.COMMAND_WORD_GROUP:
            return new CmdGroupCommandParser().parse(arguments);
        case Command.COMMAND_WORD_ALL:
            return new CmdAllCommandParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_CMD_COMMAND, CmdCommand.MESSAGE_USAGE));
        }
    }
}
