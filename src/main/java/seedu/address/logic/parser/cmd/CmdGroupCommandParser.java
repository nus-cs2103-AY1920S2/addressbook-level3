package seedu.address.logic.parser.cmd;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.cmd.CmdGroupCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses cmd commands with command word "group".
 */
public class CmdGroupCommandParser implements Parser<CmdGroupCommand> {
    @Override
    public CmdGroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CmdGroupCommand.MESSAGE_USAGE));
        }

        return new CmdGroupCommand(trimmedArgs);
    }
}
