package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.NearbyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NearbyCommand object.
 */
public class NearbyCommandParser implements Parser<NearbyCommand> {
    private static final Logger logger = LogsCenter.getLogger(NearbyCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the NearbyCommand
     * and returns a NearbyCommand object for execution.
     *
     * @param args to be parsed
     * @throws ParseException if {@code args} does not conform the expected format
     */
    @Override
    public NearbyCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        boolean isInvalid = trimmedArgs.length() == 0;
        if (isInvalid) {
            logger.info("Invalid arguments given for NearbyCommandParser");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    NearbyCommand.MESSAGE_USAGE));
        }
        return new NearbyCommand(trimmedArgs);
    }
}
