package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_TASK_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.task.TaskCommand;
import seedu.address.logic.commands.task.TaskListCommand;
import seedu.address.logic.commands.task.TaskListUnDoneCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TaskCommand object
 */
public class TaskCommandParser implements Parser<TaskCommand> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_TASK_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    @Override
    public TaskCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_TASK_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_TASK_COMMAND, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case Command.COMMAND_WORD_ADD:
            return new TaskAddCommandParser().parse(arguments);

        case Command.COMMAND_WORD_EDIT:
            return new TaskEditCommandParser().parse(arguments);

        case Command.COMMAND_WORD_DELETE:
            return new TaskDeleteCommandParser().parse(arguments);

        case Command.COMMAND_WORD_DONE:
            return new TaskMarkAsDoneCommandParser().parse(arguments);

        case Command.COMMAND_WORD_UNDONE:
            return new TaskListUnDoneCommand();

        case Command.COMMAND_WORD_SEARCH:
            return new TaskSearchCommandParser().parse(arguments);

        case Command.COMMAND_WORD_FIND:
            return new TaskFindCommandParser().parse(arguments);

        case Command.COMMAND_WORD_LIST:
            return new TaskListCommand();

        case Command.COMMAND_WORD_MODULE:
            return new TaskForOneModuleCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_TASK_COMMAND, HelpCommand.MESSAGE_USAGE));
        }
    }
}
