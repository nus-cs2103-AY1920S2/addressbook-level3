//@@author Chuayijing

package tatracker.logic.parser.session;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses user input into commands that interact with Session model.
 */
public class ClaimCommandParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(Messages.getInvalidCommandWithHelpMessage());
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case CommandWords.FILTER_MODEL:
            return new FilterClaimCommandParser().parse(arguments);

        default:
            throw new ParseException(Messages.getUnknownCommandWithHelpMessage());
        }
    }
}
