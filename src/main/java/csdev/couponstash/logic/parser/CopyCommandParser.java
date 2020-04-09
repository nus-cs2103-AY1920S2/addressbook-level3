package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.CopyCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CopyCommand object
 */
public class CopyCommandParser implements Parser<CopyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CopyCommand
     * and returns a CopyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CopyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CopyCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(
                            pe.getMessage() + "\n\n" + MESSAGE_INVALID_COMMAND_FORMAT,
                            CopyCommand.MESSAGE_USAGE
                    ),
                    pe
            );
        }
    }
}
