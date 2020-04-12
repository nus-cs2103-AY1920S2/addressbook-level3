package tatracker.logic.parser;

import static tatracker.commons.core.Messages.MESSAGE_HELP;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND;
import static tatracker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.commons.ClearCommand;
import tatracker.logic.commands.commons.ExitCommand;
import tatracker.logic.commands.commons.HelpCommand;
import tatracker.logic.commands.commons.ListCommand;
import tatracker.logic.parser.commons.GotoCommandParser;
import tatracker.logic.parser.commons.SetRateCommandParser;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.logic.parser.group.GroupCommandParser;
import tatracker.logic.parser.module.ModuleCommandParser;
import tatracker.logic.parser.session.ClaimCommandParser;
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

        /* Student View */
        case CommandWords.MODULE:
            return new ModuleCommandParser().parseCommand(arguments);

        case CommandWords.GROUP:
            return new GroupCommandParser().parseCommand(arguments);

        case CommandWords.STUDENT:
            return new StudentCommandParser().parseCommand(arguments);

        case CommandWords.SORT:
            return new SortCommandParser().parse(arguments);

        /* Session View */
        case CommandWords.SESSION:
            return new SessionCommandParser().parseCommand(arguments);

        case CommandWords.LIST:
            return new ListCommand();

        //@@author Chuayijing
        /* TSS View */
        case CommandWords.CLAIM:
            return new ClaimCommandParser().parseCommand(arguments);

        //@@author PotatoCombat
        /* Storage Operations */
        case CommandWords.CLEAR:
            return new ClearCommand();

        /* Navigation */
        case CommandWords.GOTO:
            return new GotoCommandParser().parse(arguments);

        case CommandWords.REPORT:
            return new ShowStatisticCommandParser().parse(arguments);

        case CommandWords.HELP:
            return new HelpCommand();

        case CommandWords.EXIT:
            return new ExitCommand();

        /* Others */
        case CommandWords.SET_RATE:
            return new SetRateCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
