package nasa.logic.parser;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import nasa.commons.core.index.Index;
import nasa.logic.commands.EditModuleCommand;
import nasa.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates an EditModuleCommand object.
 */
public class EditModuleCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditModuleCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE),
                    pe);
        }

        return new EditModuleCommand(index);
    }
}
