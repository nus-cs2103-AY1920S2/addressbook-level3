package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.todolist.Task;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class DeleteDeadlineCommandParser implements Parser<DeleteDeadlineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteDeadlineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteDeadlineCommand.MESSAGE_USAGE));
        }

        Task deadline = ParserUtil.parseRemoveDeadline(argMultimap.getValue(PREFIX_TASK_INDEX).get());


        return new DeleteDeadlineCommand(deadline);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
