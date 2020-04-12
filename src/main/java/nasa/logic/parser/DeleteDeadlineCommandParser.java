package nasa.logic.parser;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.NoSuchElementException;

import nasa.commons.core.index.Index;
import nasa.logic.commands.DeleteDeadlineCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

/**
 * Parser for {@code DeleteDeadlineCommand}.
 */
public class DeleteDeadlineCommandParser implements Parser<DeleteDeadlineCommand> {

    /**
     * Parses user input argument and returns a {@code DeleteDeadlineCommand}.
     * @param args user input argument
     * @return {@code DeleteDeadlineCommand}
     * @throws ParseException
     */
    public DeleteDeadlineCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteDeadlineCommand.MESSAGE_USAGE), pe);
        }

        /*
         * If PREFIX_MODULE exist, then return DeleteActivityCommand
         * else throw exception
         */
        ModuleCode moduleCode;
        try {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
            return new DeleteDeadlineCommand(index, moduleCode);
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteDeadlineCommand.MESSAGE_USAGE));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteDeadlineCommand.MESSAGE_USAGE), pe);
        }
    }
}
