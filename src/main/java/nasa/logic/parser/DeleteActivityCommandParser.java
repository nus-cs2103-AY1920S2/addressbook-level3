package nasa.logic.parser;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.NoSuchElementException;

import nasa.commons.core.index.Index;
import nasa.logic.commands.DeleteActivityCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

/**
 * Parses input arguments and creates a DeleteActivityCommand object.
 */
public class DeleteActivityCommandParser implements Parser<DeleteActivityCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteActivityCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteActivityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteActivityCommand.MESSAGE_USAGE), pe);
        }

        /*
         * If PREFIX_MODULE exist, then return DeleteActivityCommand
         * else throw exception
         */
        ModuleCode moduleCode;
        try {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
            return new DeleteActivityCommand(index, moduleCode);
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteActivityCommand.MESSAGE_USAGE));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteActivityCommand.MESSAGE_USAGE), pe);
        }
    }
}
