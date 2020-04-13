package nasa.logic.parser.module;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.NoSuchElementException;

import nasa.logic.commands.module.DeleteModuleCommand;
import nasa.logic.parser.ArgumentMultimap;
import nasa.logic.parser.ArgumentTokenizer;
import nasa.logic.parser.Parser;
import nasa.logic.parser.ParserUtil;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

//@@author kester-ng
/**
 * Parses input arguments and creates a DeleteModuleCommand object.
 */
public class DeleteModuleCommandParser implements Parser<DeleteModuleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteModuleCommand
     * and returns a DeleteModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE);
        try {
            ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
            return new DeleteModuleCommand(moduleCode);
        } catch (NoSuchElementException e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        } catch (ParseException e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, e);
        }
    }
}
