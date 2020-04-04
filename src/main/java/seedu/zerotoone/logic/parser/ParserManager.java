package seedu.zerotoone.logic.parser;

import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.DoneCommand;
import seedu.zerotoone.logic.commands.ExitCommand;
import seedu.zerotoone.logic.commands.HelpCommand;
import seedu.zerotoone.logic.commands.SkipCommand;
import seedu.zerotoone.logic.commands.StartCommand;
import seedu.zerotoone.logic.commands.StopCommand;
import seedu.zerotoone.logic.commands.exercise.ExerciseCommand;
import seedu.zerotoone.logic.commands.log.LogCommand;
import seedu.zerotoone.logic.commands.schedule.ScheduleCommand;
import seedu.zerotoone.logic.commands.workout.WorkoutCommand;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.logic.parser.exercise.ExerciseCommandParser;
import seedu.zerotoone.logic.parser.log.LogCommandParser;
import seedu.zerotoone.logic.parser.schedule.ScheduleCommandParser;
import seedu.zerotoone.logic.parser.session.DoneCommandParser;
import seedu.zerotoone.logic.parser.session.SkipCommandParser;
import seedu.zerotoone.logic.parser.session.StartCommandParser;
import seedu.zerotoone.logic.parser.session.StopCommandParser;
import seedu.zerotoone.logic.parser.workout.WorkoutCommandParser;
import seedu.zerotoone.ui.util.ViewType;

/**
 * Parses user input.
 */
public class ParserManager {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param input full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String input) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(input.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case StartCommand.COMMAND_WORD:
            System.out.println(arguments);
            return new StartCommandParser().parse(arguments);
        case StopCommand.COMMAND_WORD:
            return new StopCommandParser().parse(arguments);
        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);
        case SkipCommand.COMMAND_WORD:
            return new SkipCommandParser().parse(arguments);
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case ExerciseCommand.COMMAND_WORD:
            return new ExerciseCommandParser().parse(arguments);
        case WorkoutCommand.COMMAND_WORD:
            return new WorkoutCommandParser().parse(arguments);
        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommandParser().parse(arguments);
        case LogCommand.COMMAND_WORD:
            return new LogCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses view type for switching UI views.
     * @param input full user input string.
     * @return the view type.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ViewType parseViewType(String input) throws ParseException {
        final String[] command = input.trim().split(" ");
        final String commandWord = command[0];

        switch (commandWord) {
        case "start":
            return ViewType.SESSION_VIEW;

        case "exercise":
            return ViewType.EXERCISE_VIEW;

        case "workout":
            return ViewType.WORKOUT_VIEW;

        case "schedule":
            return ViewType.SCHEDULE_VIEW;

        case "log":
            return ViewType.LOG_VIEW;

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

    }
}
