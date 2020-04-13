package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nasa.logic.commands.ClearCommand;
import nasa.logic.commands.Command;
import nasa.logic.commands.ContinueCommand;
import nasa.logic.commands.DeleteDeadlineCommand;
import nasa.logic.commands.DeleteEventCommand;
import nasa.logic.commands.DoneCommand;
import nasa.logic.commands.EditDeadlineCommand;
import nasa.logic.commands.EditEventCommand;
import nasa.logic.commands.ExitCommand;
import nasa.logic.commands.ExportCalendarCommand;
import nasa.logic.commands.ExportQrCommand;
import nasa.logic.commands.FindCommand;
import nasa.logic.commands.HelpCommand;
import nasa.logic.commands.ListCommand;
import nasa.logic.commands.QuoteCommand;
import nasa.logic.commands.RedoCommand;
import nasa.logic.commands.RefreshCommand;
import nasa.logic.commands.RepeatDeadlineCommand;
import nasa.logic.commands.RepeatEventCommand;
import nasa.logic.commands.SortCommand;
import nasa.logic.commands.StatisticsCommand;
import nasa.logic.commands.UndoCommand;
import nasa.logic.commands.ViewCommand;
import nasa.logic.commands.addcommands.AddDeadlineCommand;
import nasa.logic.commands.addcommands.AddEventCommand;
import nasa.logic.commands.module.AddModuleCommand;
import nasa.logic.commands.module.DeleteModuleCommand;
import nasa.logic.commands.module.EditModuleCommand;
import nasa.logic.parser.addcommandparser.AddDeadlineCommandParser;
import nasa.logic.parser.addcommandparser.AddEventCommandParser;
import nasa.logic.parser.exceptions.ParseException;
import nasa.logic.parser.module.AddModuleCommandParser;
import nasa.logic.parser.module.DeleteModuleCommandParser;
import nasa.logic.parser.module.EditModuleCommandParser;

/**
 * Parses user input.
 */
public class NasaBookParser {

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

        switch (commandWord.toLowerCase()) {

        case AddModuleCommand.COMMAND_WORD:
            return new AddModuleCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case AddDeadlineCommand.COMMAND_WORD:
            return new AddDeadlineCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ContinueCommand.COMMAND_WORD:
            return new ContinueCommandParser().parse(arguments);

        case DeleteDeadlineCommand.COMMAND_WORD:
            return new DeleteDeadlineCommandParser().parse(arguments);

        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);

        case DeleteModuleCommand.COMMAND_WORD:
            return new DeleteModuleCommandParser().parse(arguments);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        case EditDeadlineCommand.COMMAND_WORD:
            return new EditDeadlineCommandParser().parse(arguments);

        case EditEventCommand.COMMAND_WORD:
            return new EditEventCommandParser().parse(arguments);

        case EditModuleCommand.COMMAND_WORD:
            return new EditModuleCommandParser().parse(arguments);

        case RefreshCommand.COMMAND_WORD:
            return new RefreshCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ExportQrCommand.COMMAND_WORD:
            return new ExportQrCommand();

        case ExportCalendarCommand.COMMAND_WORD:
            return new ExportCalendarCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case RepeatDeadlineCommand.COMMAND_WORD:
            return new RepeatDeadlineCommandParser().parse(arguments);

        case RepeatEventCommand.COMMAND_WORD:
            return new RepeatEventCommandParser().parse(arguments);

        case QuoteCommand.COMMAND_WORD:
            return new QuoteCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
