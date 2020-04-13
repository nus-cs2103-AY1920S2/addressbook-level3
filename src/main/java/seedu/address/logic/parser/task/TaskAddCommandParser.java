package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_AT_WITHOUT_ON_ERROR;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import java.util.stream.Stream;

import seedu.address.logic.commands.task.TaskAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;
import seedu.address.model.util.Description;

/**
 * Parses input arguments and creates a new TaskAddCommand object
 */
public class TaskAddCommandParser implements Parser<TaskAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TaskAddCommand
     * and returns a TaskAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TaskAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_MODULE_CODE, PREFIX_DESCRIPTION, PREFIX_ON, PREFIX_AT);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskAddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.numOfValuesPresent(PREFIX_MODULE_CODE) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_DESCRIPTION) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_DESCRIPTION));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_ON) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_ON));
        }
        if (argMultimap.numOfValuesPresent(PREFIX_AT) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_AT));
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE));
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION));

        if (!arePrefixesPresent(argMultimap, PREFIX_ON)) {
            if (arePrefixesPresent(argMultimap, PREFIX_AT)) {
                throw new ParseException(MESSAGE_AT_WITHOUT_ON_ERROR);
            }
            return new TaskAddCommand(Task.makeNonScheduledTask(description, moduleCode));
        } else {
            TaskDateTime taskDateTime;
            String date = argMultimap.getValue(PREFIX_ON);
            if (arePrefixesPresent(argMultimap, PREFIX_AT)) {
                taskDateTime = ParserUtil.parseDateTimeForTask(date, argMultimap.getValue(PREFIX_AT));
            } else {
                taskDateTime = ParserUtil.parseDateForTask(date);
            }
            return new TaskAddCommand(Task.makeScheduledTask(description, taskDateTime, moduleCode));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix) != null);
    }
}
