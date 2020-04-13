package seedu.jelphabot.logic.parser;

import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.jelphabot.logic.commands.AddCommand;
import seedu.jelphabot.logic.commands.CalendarCommand;
import seedu.jelphabot.logic.commands.ClearCommand;
import seedu.jelphabot.logic.commands.Command;
import seedu.jelphabot.logic.commands.DeleteCommand;
import seedu.jelphabot.logic.commands.DeleteReminderCommand;
import seedu.jelphabot.logic.commands.DoneCommand;
import seedu.jelphabot.logic.commands.EditCommand;
import seedu.jelphabot.logic.commands.ExitCommand;
import seedu.jelphabot.logic.commands.FindCommand;
import seedu.jelphabot.logic.commands.HelpCommand;
import seedu.jelphabot.logic.commands.ListCommand;
import seedu.jelphabot.logic.commands.ProductivityCommand;
import seedu.jelphabot.logic.commands.ReminderCommand;
import seedu.jelphabot.logic.commands.ReminderTabCommand;
import seedu.jelphabot.logic.commands.ShowCompletedCommand;
import seedu.jelphabot.logic.commands.ShowIncompleteCommand;
import seedu.jelphabot.logic.commands.StartTimerCommand;
import seedu.jelphabot.logic.commands.StopTimerCommand;
import seedu.jelphabot.logic.commands.SummaryCommand;
import seedu.jelphabot.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class JelphaBotParser {

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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteReminderCommand.COMMAND_WORD:
            return new DeleteReminderCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return getCommand(arguments, new ClearCommand(), ClearCommand.MESSAGE_USAGE);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD: // fallthrough
        case ListCommand.COMMAND_SHORTCUT: // fallthrough
        case ListCommand.COMMAND_SHORTCUT_TWO:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return getCommand(arguments, new ExitCommand(), ExitCommand.MESSAGE_USAGE);

        case HelpCommand.COMMAND_WORD:
            return getCommand(arguments, new HelpCommand(), HelpCommand.MESSAGE_USAGE);

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommandParser().parse(arguments);

        case ReminderTabCommand.COMMAND_WORD: // fallthrough
        case ReminderTabCommand.COMMAND_WORD_UPPER: // fallthrough
        case ReminderTabCommand.COMMAND_WORD_LOWER:
            return getCommand(arguments, new ReminderTabCommand(), ReminderTabCommand.MESSAGE_USAGE);

        case ShowCompletedCommand.COMMAND_WORD:
            return getCommand(arguments, new ShowCompletedCommand(), ShowCompletedCommand.MESSAGE_USAGE);

        case ShowIncompleteCommand.COMMAND_WORD:
            return getCommand(arguments, new ShowIncompleteCommand(), ShowIncompleteCommand.MESSAGE_USAGE);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        case CalendarCommand.COMMAND_WORD: // fallthrough
        case CalendarCommand.COMMAND_SHORTCUT_UPPER: // fallthrough
        case CalendarCommand.COMMAND_SHORTCUT_LOWER:
            return new CalendarCommandParser().parse(arguments);

        case SummaryCommand.COMMAND_WORD: // fallthrough
        case SummaryCommand.COMMAND_SHORTCUT_UPPER: // fallthrough
        case SummaryCommand.COMMAND_SHORTCUT_LOWER:
            return getCommand(arguments, new SummaryCommand(), SummaryCommand.MESSAGE_USAGE);

        case ProductivityCommand.COMMAND_WORD: // fallthrough
        case ProductivityCommand.COMMAND_SHORTCUT_UPPER: // fallthrough
        case ProductivityCommand.COMMAND_SHORTCUT_LOWER:
            return getCommand(arguments, new ProductivityCommand(), ProductivityCommand.MESSAGE_USAGE);

        case StartTimerCommand.COMMAND_WORD:
            return new StartTimerCommandParser().parse(arguments);

        case StopTimerCommand.COMMAND_WORD:
            return new StopTimerCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private static Command getCommand(String arguments, Command command, String message) throws ParseException {
        if (arguments.length() > 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, message));
        }
        return command;
    }

}
