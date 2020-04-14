package fithelper.logic.parser.revoke;

import fithelper.commons.core.Messages;
import fithelper.logic.commands.UndoCommand;
import fithelper.logic.parser.Parser;
import fithelper.logic.parser.exceptions.ParseException;

/**
 * A parser that parses {@code UndoCommand}.
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    @Override
    public UndoCommand parse(String args) throws ParseException {

        if (!args.isBlank()) {
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }

        return new UndoCommand();
    }

}
