package com.flashspeed.logic.parser.cardparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.cardcommands.DeleteCardCommand;
import com.flashspeed.logic.parser.Parser;
import com.flashspeed.logic.parser.ParserUtil;
import com.flashspeed.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCardCommand object.
 */
public class DeleteCardCommandParser implements Parser<DeleteCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCardCommand
     * and returns a DeleteCardCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteCardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCardCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCardCommand.MESSAGE_USAGE), pe);
        }
    }
}
