package cardibuddy.logic.parser;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.commands.OpenCommand;
import cardibuddy.logic.parser.exceptions.ParseException;

import java.util.logging.Logger;

import static cardibuddy.logic.commands.OpenCommand.MESSAGE_OPEN_DECK_SUCCESS;

/**
 * Parses input arguments and creates a new OpenCommand object
 */
public class OpenCommandParser implements Parser<OpenCommand> {

    private static Logger logger = Logger.getLogger(OpenCommand.COMMAND_WORD);
    /**
     * Parses the given {@code String} of arguments in the context of the OpenCommand
     * and returns a OpenCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new OpenCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_OPEN_DECK_SUCCESS, OpenCommand.MESSAGE_USAGE), pe);
        }
    }
}
