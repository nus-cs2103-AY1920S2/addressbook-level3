package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.UnarchiveCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnarchiveCommand Object
 */
public class UnarchiveCommandParser implements Parser<UnarchiveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnarchiveCommand
     * and returns an UnarchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public UnarchiveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnarchiveCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(
                            pe.getMessage() + "\n\n" + MESSAGE_INVALID_COMMAND_FORMAT,
                            UnarchiveCommand.MESSAGE_USAGE
                    ),
                    pe
            );
        }
    }
}
