package tatracker.logic.parser;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tatracker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tatracker.logic.commands.ClearCommand;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.DeleteCommand;
import tatracker.logic.commands.ExitCommand;
import tatracker.logic.commands.FindCommand;
import tatracker.logic.commands.HelpCommand;
import tatracker.logic.commands.ListCommand;
import tatracker.logic.commands.SortCommand;
import tatracker.logic.commands.student.EditStudentCommand;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.logic.parser.group.GroupCommandParser;
import tatracker.logic.parser.module.ModuleCommandParser;
import tatracker.logic.parser.student.EditStudentCommandParser;
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
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

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

        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
