package seedu.address.logic.parser.action;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.action.RedoCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses a redo command.
 */
public class RedoCommandParser implements Parser<RedoCommand> {
    @Override
    public RedoCommand parse(String userInput) throws ParseException {
        if (!userInput.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }
        return new RedoCommand();
    }
}
