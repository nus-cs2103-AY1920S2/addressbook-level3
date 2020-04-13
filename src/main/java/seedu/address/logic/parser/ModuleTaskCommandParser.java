package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETASK_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESC;

import java.util.stream.Stream;

import seedu.address.logic.commands.taskcommand.addcommand.ModuleTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.Priority;

/**
 * Parses input arguments and creates a new ModuleTaskCommand object
 */
public class ModuleTaskCommandParser implements Parser<ModuleTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ModuleTaskCommand
     * and returns an ModuleTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModuleTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_DESC, PREFIX_MODULE_CODE,
                        PREFIX_MODULETASK_TIMING, PREFIX_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_DESC, PREFIX_MODULE_CODE,
                PREFIX_MODULETASK_TIMING, PREFIX_PRIORITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleTaskCommand.MESSAGE_USAGE));
        }

        String trimmedDesc = argMultimap.getValue(PREFIX_TASK_DESC).get().trim();
        ModuleCode moduleCode = ParserUtil.parseModuleCode(
                argMultimap.getValue(PREFIX_MODULE_CODE).get());
        String timing = argMultimap.getValue(PREFIX_MODULETASK_TIMING).get();
        if (!Task.isValidDate(timing)) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE, ModuleTaskCommand.MESSAGE_USAGE));
        }
        Priority p = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        ModuleTask moduleTask = new ModuleTask(trimmedDesc, moduleCode, timing, p);

        return new ModuleTaskCommand(moduleTask);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
