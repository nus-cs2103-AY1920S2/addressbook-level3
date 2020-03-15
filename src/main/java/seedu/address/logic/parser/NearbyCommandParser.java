package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NearbyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NearbyCommand object.
 */
public class NearbyCommandParser implements Parser<NearbyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NearbyCommand
     * and returns a NearbyCommand object for execution.
     *
     * @param args to be parsed
     * @throws ParseException if {@code args} does not conform the expected format
     */
    @Override
    public NearbyCommand parse(String args) throws ParseException {
        try {
            Index postalSector = ParserUtil.parseIndex(args);
            return new NearbyCommand(postalSector);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NearbyCommand.MESSAGE_USAGE), pe);
        }
    }
}
