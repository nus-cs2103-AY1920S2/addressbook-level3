package fithelper.logic.parser.revoke;

import fithelper.commons.core.Messages;
import fithelper.logic.commands.RedoCommand;
import fithelper.logic.parser.Parser;
import fithelper.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code RedoCommand}.
 */
public class RedoCommandParser implements Parser<RedoCommand> {

    @Override
    public RedoCommand parse(String args) throws ParseException {

        if (!args.isBlank()) {
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }

        return new RedoCommand();
    }

}

