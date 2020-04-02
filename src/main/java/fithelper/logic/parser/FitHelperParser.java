package fithelper.logic.parser;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fithelper.logic.commands.AddCommand;
import fithelper.logic.commands.AddWeightCommand;
import fithelper.logic.commands.CalendarCommand;
import fithelper.logic.commands.CheckCommand;
import fithelper.logic.commands.ClearCommand;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.DeleteCommand;
import fithelper.logic.commands.EditCommand;
import fithelper.logic.commands.ExitCommand;
import fithelper.logic.commands.FindCommand;
import fithelper.logic.commands.HelpCommand;
import fithelper.logic.commands.HomeCommand;
import fithelper.logic.commands.ListCommand;
import fithelper.logic.commands.ProfileCommand;
import fithelper.logic.commands.RedoCommand;
import fithelper.logic.commands.ReminderCommand;
import fithelper.logic.commands.ReportCommand;
import fithelper.logic.commands.SortCommand;
import fithelper.logic.commands.TodayCommand;
import fithelper.logic.commands.UndoCommand;
import fithelper.logic.commands.UpdateCommand;
import fithelper.logic.commands.WeightCommand;

import fithelper.logic.commands.diary.AddDiaryCommand;
import fithelper.logic.commands.diary.AppendDiaryCommand;
import fithelper.logic.commands.diary.ClearDiaryCommand;
import fithelper.logic.commands.diary.DeleteDiaryCommand;
import fithelper.logic.commands.diary.DiaryCommand;
import fithelper.logic.commands.diary.EditDiaryCommand;
import fithelper.logic.commands.diary.FindDiaryCommand;
import fithelper.logic.parser.diary.AddDiaryCommandParser;
import fithelper.logic.parser.diary.AppendDiaryCommandParser;
import fithelper.logic.parser.diary.ClearDiaryCommandParser;
import fithelper.logic.parser.diary.DeleteDiaryCommandParser;
import fithelper.logic.parser.diary.EditDiaryCommandParser;
import fithelper.logic.parser.diary.FindDiaryCommandParser;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.logic.parser.revoke.RedoCommandParser;
import fithelper.logic.parser.revoke.UndoCommandParser;

/**
 * Parses user input.
 */
public class FitHelperParser {

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

        case AddDiaryCommand.COMMAND_WORD:
            return new AddDiaryCommandParser().parse(arguments);

        case AppendDiaryCommand.COMMAND_WORD:
            return new AppendDiaryCommandParser().parse(arguments);

        case AddWeightCommand.COMMAND_WORD:
            return new AddWeightCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditDiaryCommand.COMMAND_WORD:
            return new EditDiaryCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteDiaryCommand.COMMAND_WORD:
            return new DeleteDiaryCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ClearDiaryCommand.COMMAND_WORD:
            return new ClearDiaryCommandParser().parse(arguments);

        case CheckCommand.COMMAND_WORD:
            return new CheckCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindDiaryCommand.COMMAND_WORD:
            return new FindDiaryCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD_1:
            // fall through
        case ExitCommand.COMMAND_WORD_2:
            // fall through
        case ExitCommand.COMMAND_WORD_3:
            return new ExitCommand();

        case TodayCommand.COMMAND_WORD:
            return new TodayCommandParser().parse(arguments);

        case DiaryCommand.COMMAND_WORD:
            return new DiaryCommandParser().parse(arguments);

        case CalendarCommand.COMMAND_WORD:
            return new CalendarParser().parse(arguments);

        case ReportCommand.COMMAND_WORD:
            return new ReportCommand();

        case ProfileCommand.COMMAND_WORD:
            return new ProfileCommand();

        case WeightCommand.COMMAND_WORD:
            return new WeightCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case HomeCommand.COMMAND_WORD:
            return new HomeCommand();

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommand();

        case UpdateCommand.COMMAND_WORD:
            return new UpdateCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

