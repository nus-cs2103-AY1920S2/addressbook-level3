package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SORTING_PARAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTING;

import java.util.stream.Stream;

import seedu.address.logic.commands.taskcommand.sortcommand.SortTasksCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortTasksCommand object
 */
public class SortTasksCommandParser implements Parser<SortTasksCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ModuleTaskCommand
     * and returns an ModuleTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortTasksCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORTING);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORTING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTasksCommand.MESSAGE_USAGE));
        }

        String sortingParam = argMultimap.getValue(PREFIX_SORTING).get().trim();
        if (!sortingParam.equals("priority") && !sortingParam.equals("date")) {
            throw new ParseException(MESSAGE_INVALID_SORTING_PARAM + " " + SortTasksCommand.MESSAGE_USAGE);
        }

        return new SortTasksCommand(sortingParam);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
