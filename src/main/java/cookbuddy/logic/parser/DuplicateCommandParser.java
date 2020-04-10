package cookbuddy.logic.parser;

import static cookbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import cookbuddy.commons.core.index.Index;
import cookbuddy.logic.commands.DuplicateCommand;
import cookbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DuplicateCommand object
 */
public class DuplicateCommandParser implements Parser<DuplicateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DuplicateCommand
     * and returns a DuplicateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DuplicateCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DuplicateCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, pe.getLocalizedMessage())
                    + "\nFor a command summary, type \"help duplicate\"");
        }
    }

}
