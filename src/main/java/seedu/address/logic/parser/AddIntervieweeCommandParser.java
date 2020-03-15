package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddIntervieweeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new object of type AddIntervieweeCommand
 */
public class AddIntervieweeCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddIntervieweeCommand
     * and returns an AddIntervieweeCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddIntervieweeCommand parse(String arguments) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arguments, PREFIX_ALIAS);

        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS) || !argMultimap.getPreamble().isEmpty()) {
            return new AddIntervieweeCommand(arguments);
        } else {
            return new AddIntervieweeCommand(arguments, argMultimap.getValue(PREFIX_ALIAS).get());
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
