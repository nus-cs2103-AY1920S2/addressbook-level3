package com.flashspeed.logic.parser.gameparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.gamecommands.PlayCommand;
import com.flashspeed.logic.parser.Parser;
import com.flashspeed.logic.parser.ParserUtil;
import com.flashspeed.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PlayCommand object
 */
public class PlayCommandParser implements Parser<PlayCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PlayCommand
     * and returns an PlayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PlayCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PlayCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlayCommand.MESSAGE_USAGE), pe);
        }
    }
}
