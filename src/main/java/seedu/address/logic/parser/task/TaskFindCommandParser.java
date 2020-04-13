package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.task.TaskFindCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new TaskFindCommand objecNt
 */
public class TaskFindCommandParser implements Parser<TaskFindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TaskFindCommand
     * and returns a TaskFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TaskFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskFindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        assert (nameKeywords.length > 0) : "Finding keywords should be provided";

        return new TaskFindCommand(new TaskContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
