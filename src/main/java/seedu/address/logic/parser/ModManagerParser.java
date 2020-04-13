package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.action.RedoCommandParser;
import seedu.address.logic.parser.action.UndoCommandParser;
import seedu.address.logic.parser.calendar.CalCommandParser;
import seedu.address.logic.parser.cmd.CmdCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.facilitator.FacilCommandParser;
import seedu.address.logic.parser.lesson.LessonCommandParser;
import seedu.address.logic.parser.module.ModuleCommandParser;
import seedu.address.logic.parser.task.TaskCommandParser;

/**
 * Parses user input.
 */
public class ModManagerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(\\s*)(?<arguments>.*)");

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
        case Command.COMMAND_WORD_CLEAR:
            return new ClearCommand();

        case Command.COMMAND_GROUP_EXIT:
            return new ExitCommand();

        case Command.COMMAND_WORD_HELP:
            return new HelpCommand();

        case Command.COMMAND_GROUP_MOD:
            return new ModuleCommandParser().parse(arguments);

        case Command.COMMAND_GROUP_FACIL:
            return new FacilCommandParser().parse(arguments);

        case Command.COMMAND_GROUP_CLASS:
            return new LessonCommandParser().parse(arguments);

        case Command.COMMAND_GROUP_CAL:
            return new CalCommandParser().parse(arguments);

        case Command.COMMAND_GROUP_TASK:
            return new TaskCommandParser().parse(arguments);

        case Command.COMMAND_GROUP_CMD:
            return new CmdCommandParser().parse(arguments);

        case Command.COMMAND_GROUP_UNDO:
            return new UndoCommandParser().parse(arguments);

        case Command.COMMAND_GROUP_REDO:
            return new RedoCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }
}
