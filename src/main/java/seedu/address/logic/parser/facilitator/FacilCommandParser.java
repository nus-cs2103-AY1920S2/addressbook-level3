package seedu.address.logic.parser.facilitator;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_FACILITATOR_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.facilitator.FacilCommand;
import seedu.address.logic.commands.facilitator.FacilListCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FacilCommand object.
 */
public class FacilCommandParser implements Parser<FacilCommand> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_FACIL_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    @Override
    public FacilCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_FACIL_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_FACILITATOR_COMMAND, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case Command.COMMAND_WORD_ADD:
            return new FacilAddCommandParser().parse(arguments);

        case Command.COMMAND_WORD_EDIT:
            return new FacilEditCommandParser().parse(arguments);

        case Command.COMMAND_WORD_DELETE:
            return new FacilDeleteCommandParser().parse(arguments);

        case Command.COMMAND_WORD_FIND:
            return new FacilFindCommandParser().parse(arguments);

        case Command.COMMAND_WORD_LIST:
            return new FacilListCommand();

        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_FACILITATOR_COMMAND, HelpCommand.MESSAGE_USAGE));
        }
    }
}
