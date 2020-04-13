package seedu.address.logic.parser.lesson;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_LESSON_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.lesson.LessonCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LessonCommand object
 */
public class LessonCommandParser implements Parser<LessonCommand> {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_LESSON_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    @Override
    public LessonCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_LESSON_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_LESSON_COMMAND, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case Command.COMMAND_WORD_ADD:
            return new LessonAddCommandParser().parse(arguments);

        case Command.COMMAND_WORD_EDIT:
            return new LessonEditCommandParser().parse(arguments);

        case Command.COMMAND_WORD_DELETE:
            return new LessonDeleteCommandParser().parse(arguments);

        case Command.COMMAND_WORD_FIND:
            return new LessonFindCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_LESSON_COMMAND, HelpCommand.MESSAGE_USAGE));
        }
    }
}
