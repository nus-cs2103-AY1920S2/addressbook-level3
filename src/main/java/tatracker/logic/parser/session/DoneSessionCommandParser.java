//@@author Eclmist

package tatracker.logic.parser.session;

import static tatracker.commons.core.Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX;

import tatracker.commons.core.index.Index;
import tatracker.logic.commands.session.DoneSessionCommand;
import tatracker.logic.parser.Parser;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DoneSessionCommand object
 */
public class DoneSessionCommandParser implements Parser<DoneSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneSessionCommand
     * and returns an DoneSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneSessionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DoneSessionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_SESSION_DISPLAYED_INDEX, pe);
        }
    }
}
