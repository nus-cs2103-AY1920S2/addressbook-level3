//@@author fatin99

package tatracker.logic.parser.commons;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.commons.GotoCommand;
import tatracker.logic.commands.commons.GotoCommand.Tab;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new GotoCommand object
 */
public class GotoCommandParser implements Parser<GotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GotoCommand
     * and returns a GotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GotoCommand parse(String args) throws ParseException {
        try {
            Tab tabName = ParserUtil.parseTabName(args);
            return new GotoCommand(tabName);
        } catch (ParseException pe) {
            throw new ParseException(Messages.getInvalidCommandMessage(GotoCommand.DETAILS.getUsage()));
        }
    }
}
