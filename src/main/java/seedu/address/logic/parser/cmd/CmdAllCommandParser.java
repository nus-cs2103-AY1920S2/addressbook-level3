package seedu.address.logic.parser.cmd;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.cmd.CmdAllCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses cmd commands with command group "all".
 */
public class CmdAllCommandParser implements Parser<CmdAllCommand> {
    @Override
    public CmdAllCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CmdAllCommand.MESSAGE_USAGE));
        }

        return new CmdAllCommand();
    }
}
