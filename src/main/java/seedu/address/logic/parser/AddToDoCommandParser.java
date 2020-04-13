package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESC;

import java.util.stream.Stream;

import seedu.address.logic.commands.taskcommand.addcommand.AddDeadlineCommand;
import seedu.address.logic.commands.taskcommand.addcommand.AddToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calender.Task;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddToDoCommandParser implements Parser<AddToDoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddToDoCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_DESC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeadlineCommand.MESSAGE_USAGE));
        }

        Task todo = ParserUtil.parseTodo(argMultimap.getValue(PREFIX_TASK_DESC).get());


        return new AddToDoCommand(todo);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
