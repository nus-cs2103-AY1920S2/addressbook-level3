package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.taskcommand.deletecommand.DeleteDeadlineCommand;
import seedu.address.logic.commands.taskcommand.deletecommand.DeleteModuleTaskCommand;
import seedu.address.logic.commands.taskcommand.deletecommand.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.ModuleCode;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap1 =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_INDEX);

        ArgumentMultimap argMultimap2 =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_TASK_INDEX);

        if (arePrefixesPresent(argMultimap1, PREFIX_TASK_INDEX)
                && argMultimap1.getPreamble().isEmpty()) {
            Task deadline = ParserUtil.parseRemoveDeadline(argMultimap1.getValue(PREFIX_TASK_INDEX).get());
            return new DeleteDeadlineCommand(deadline);
        } else if (arePrefixesPresent(argMultimap2, PREFIX_MODULE_CODE, PREFIX_TASK_INDEX)
                && argMultimap2.getPreamble().isEmpty()) {
            ModuleCode moduleCode = ParserUtil.parseModuleCode(
                    argMultimap2.getValue(PREFIX_MODULE_CODE).get());
            Index index = ParserUtil.parseIndex(argMultimap2.getValue(PREFIX_TASK_INDEX).get());
            return new DeleteModuleTaskCommand(moduleCode, index);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteTaskCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
