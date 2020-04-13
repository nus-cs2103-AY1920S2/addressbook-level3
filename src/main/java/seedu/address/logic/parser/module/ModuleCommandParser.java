package seedu.address.logic.parser.module;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_MODULE_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.module.ModuleCommand;
import seedu.address.logic.commands.module.ModuleListCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ModuleCommand object.
 */
public class ModuleCommandParser implements Parser<ModuleCommand> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_MODULE_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    @Override
    public ModuleCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_MODULE_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_MODULE_COMMAND, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case Command.COMMAND_WORD_ADD:
            return new ModuleAddCommandParser().parse(arguments);

        case Command.COMMAND_WORD_DELETE:
            return new ModuleDeleteCommandParser().parse(arguments);

        case Command.COMMAND_WORD_EDIT:
            return new ModuleEditCommandParser().parse(arguments);

        case Command.COMMAND_WORD_LIST:
            return new ModuleListCommand();

        case Command.COMMAND_WORD_VIEW:
            return new ModuleViewCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_MODULE_COMMAND, HelpCommand.MESSAGE_USAGE));
        }
    }
}
