package cookbuddy.logic.parser;

import cookbuddy.commons.core.index.Index;
import cookbuddy.logic.commands.FavCommand;
import cookbuddy.logic.parser.exceptions.ParseException;

import static cookbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class FavCommandParser implements Parser<FavCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavCommand.MESSAGE_USAGE), pe);
        }
    }

}
