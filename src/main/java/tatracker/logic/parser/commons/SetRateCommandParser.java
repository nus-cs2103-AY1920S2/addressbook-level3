//@@author fatin99

package tatracker.logic.parser.commons;

import java.util.logging.Logger;

import tatracker.commons.core.LogsCenter;
import tatracker.logic.commands.commons.SetRateCommand;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetRateCommand object
 */
public class SetRateCommandParser implements Parser<SetRateCommand> {

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the SetRateCommand
     * and returns a SetRateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetRateCommand parse(String args) throws ParseException {
        int rate = ParserUtil.parseRate(args);
        logger.fine("return new SetRateCommand:" + rate);
        return new SetRateCommand(rate);
    }
}
