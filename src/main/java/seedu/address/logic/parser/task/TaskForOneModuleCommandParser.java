package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.task.TaskForOneModuleCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.TaskContainsInModulePredicate;

/**
 * Parses input arguments and creates a new TaskFindCommand objecNt
 */
public class TaskForOneModuleCommandParser implements Parser<TaskForOneModuleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TaskFindCommand
     * and returns a TaskFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TaskForOneModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_MODULE_CODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TaskForOneModuleCommand.MESSAGE_USAGE));
        }

        if (argMultimap.numOfValuesPresent(PREFIX_MODULE_CODE) > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_ARGUMENTS, "one", PREFIX_MODULE_CODE));
        }

        ModuleCode modCode = ParserUtil.parseModuleCode(
                argMultimap.getValue(PREFIX_MODULE_CODE)).getClone(); // returns a defensive copy

        String moduleCode = modCode.toString();

        return new TaskForOneModuleCommand(new TaskContainsInModulePredicate(moduleCode));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix) != null);
    }
}
