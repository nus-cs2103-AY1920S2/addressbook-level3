package tatracker.logic.parser;

import static tatracker.commons.core.Messages.MESSAGE_HELP;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND;
import static tatracker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.DeleteCommand;
import tatracker.logic.commands.commons.ClearCommand;
import tatracker.logic.commands.commons.ExitCommand;
import tatracker.logic.commands.commons.GotoCommand;
import tatracker.logic.commands.commons.HelpCommand;
import tatracker.logic.commands.commons.ListCommand;
import tatracker.logic.commands.statistic.ShowStatisticCommand;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.logic.parser.group.GroupCommandParser;
import tatracker.logic.parser.module.ModuleCommandParser;
import tatracker.logic.parser.session.SessionCommandParser;
import tatracker.logic.parser.sort.SortCommandParser;
import tatracker.logic.parser.statistic.ShowStatisticCommandParser;
import tatracker.logic.parser.student.StudentCommandParser;

/**
 * Parses user input.
 */
public class TaTrackerParser {

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
        if (userInput.isEmpty()) {
            throw new ParseException(MESSAGE_HELP);
        }

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND + MESSAGE_HELP);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case CommandWords.CLAIM:
            return new ClaimCommandParser().parseCommand(arguments);

        case CommandWords.STUDENT:
            return new StudentCommandParser().parseCommand(arguments);

        case CommandWords.MODULE:
            return new ModuleCommandParser().parseCommand(arguments);

        case CommandWords.GROUP:
            return new GroupCommandParser().parseCommand(arguments);

        case CommandWords.SESSION:
            return new SessionCommandParser().parseCommand(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case CommandWords.FIND:
            return new FindCommandParser().parse(arguments);

        case GotoCommand.COMMAND_WORD:
            return new GotoCommandParser().parse(arguments);

        case CommandWords.LIST:
            return new ListCommand();

        case CommandWords.CLEAR:
            return new ClearCommand();

        case CommandWords.EXIT:
            return new ExitCommand();

        case CommandWords.HELP:
            return new HelpCommand();

        case CommandWords.SORT:
            return new SortCommandParser().parse(arguments);

        case ShowStatisticCommand.COMMAND_WORD:
            return new ShowStatisticCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
