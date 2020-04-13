package nasa.logic.parser;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.NoSuchElementException;

import nasa.commons.core.index.Index;
import nasa.logic.commands.DeleteEventCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

//@@author kester-ng
/**
 * Constructor to delete an event.
 * Parser for {@code DeleteEventCommand}.
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {

    /**
     * Parses user input argument and returns a {@code DeleteEventCommand}.
     * @param args user input argument
     * @return {@code DeleteEventCommand}
     * @throws ParseException
     */
    public DeleteEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE), pe);
        }

        /*
         * If PREFIX_MODULE exist, then return DeleteActivityCommand
         * else throw exception
         */
        ModuleCode moduleCode;
        try {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
            return new DeleteEventCommand(index, moduleCode);
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, pe);
        }
    }
}
