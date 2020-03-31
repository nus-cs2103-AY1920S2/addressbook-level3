package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_SORT_METHOD;

import nasa.logic.commands.EditModuleCommand;
import nasa.logic.commands.SortCommand;
import nasa.logic.commands.addcommands.AddDeadlineCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.SortMethod;

import java.util.NoSuchElementException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT_METHOD);

        SortMethod sortMethod;

        try {
            sortMethod = ParserUtil.parseSortMethod(argMultimap.getValue(PREFIX_SORT_METHOD).get());
        } catch (NoSuchElementException ne) { // case when no sort method is provided
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE),
                    ne);
        }
        return new SortCommand(sortMethod);
    }
}
