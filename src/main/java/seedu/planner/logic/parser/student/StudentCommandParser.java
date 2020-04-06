package seedu.planner.logic.parser.student;

import static seedu.planner.commons.core.Messages.MESSAGE_UNKNOWN_SUBCOMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.planner.logic.commands.student.StudentActiveCommand;
import seedu.planner.logic.commands.student.StudentAddCommand;
import seedu.planner.logic.commands.student.StudentCommand;
import seedu.planner.logic.commands.student.StudentGradeCommand;
import seedu.planner.logic.commands.student.StudentListCommand;
import seedu.planner.logic.commands.student.StudentRemoveCommand;
import seedu.planner.logic.parser.Parser;
import seedu.planner.logic.parser.exceptions.ParseException;

public class StudentCommandParser implements Parser<StudentCommand> {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+) ?(?<arguments>.*)");

    /**
     * Parses user subcommand input into command for execution.
     *
     * @param userInput user subcommand input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StudentCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_SUBCOMMAND, StudentCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {
        case StudentRemoveCommand.COMMAND_WORD:
            return new StudentRemoveCommandParser().parse(arguments);

        case StudentAddCommand.COMMAND_WORD:
            return new StudentAddCommandParser().parse(arguments);

        case StudentActiveCommand.COMMAND_WORD:
            return new StudentActiveCommandParser().parse(arguments);

        case StudentListCommand.COMMAND_WORD:
            return new StudentListCommand();

        case StudentGradeCommand.COMMAND_WORD:
            return new StudentGradeCommand();

        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_SUBCOMMAND, StudentCommand.MESSAGE_USAGE));
        }
    }
}
