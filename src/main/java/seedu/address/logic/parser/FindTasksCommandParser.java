package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETASK_TIMING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_CAT;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.taskcommand.findcommand.FindTasksByCatCommand;
import seedu.address.logic.commands.taskcommand.findcommand.FindTasksByDateCommand;
import seedu.address.logic.commands.taskcommand.findcommand.FindTasksByModuleCodeCommand;
import seedu.address.logic.commands.taskcommand.findcommand.FindTasksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calender.CatContainsKeywordsPredicate;
import seedu.address.model.calender.DateContainKeywordsPredicate;
import seedu.address.model.calender.Deadline;
import seedu.address.model.calender.ModuleCodeContainKeywordsPredicate;
import seedu.address.model.nusmodule.ModuleCode;

/**
 * Parses input arguments and creates a new FindTasksCommand object
 */
public class FindTasksCommandParser implements Parser<FindTasksCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTasksCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap1 =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);

        ArgumentMultimap argMultimap2 =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULETASK_TIMING);

        ArgumentMultimap argMultimap3 =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_CAT);

        if (arePrefixesPresent(argMultimap1, PREFIX_MODULE_CODE)
                && argMultimap1.getPreamble().isEmpty()) {

            ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap1.getValue(PREFIX_MODULE_CODE).get());
            return new FindTasksByModuleCodeCommand(
                    new ModuleCodeContainKeywordsPredicate(Arrays.asList(moduleCode.toString())));

        } else if (arePrefixesPresent(argMultimap2, PREFIX_MODULETASK_TIMING)
                && argMultimap2.getPreamble().isEmpty()) {

            String date = argMultimap2.getValue(PREFIX_MODULETASK_TIMING).get().trim();
            if (Deadline.isValidDate(date)) {
                return new FindTasksByDateCommand(new DateContainKeywordsPredicate(Arrays.asList(date)));
            } else {
                throw new ParseException("Invalid date! format:{dd-mm-yyyy}");
            }

        } else if (arePrefixesPresent(argMultimap3, PREFIX_TASK_CAT)
                && argMultimap3.getPreamble().isEmpty()) {

            String cat = argMultimap3.getValue(PREFIX_TASK_CAT).get().trim();
            return new FindTasksByCatCommand(new CatContainsKeywordsPredicate(Arrays.asList(cat)));

        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindTasksCommand.MESSAGE_USAGE));
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
