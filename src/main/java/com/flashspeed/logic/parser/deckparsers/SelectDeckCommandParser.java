package com.flashspeed.logic.parser.deckparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.deckcommands.SelectDeckCommand;
import com.flashspeed.logic.parser.Parser;
import com.flashspeed.logic.parser.ParserUtil;
import com.flashspeed.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectDeckCommandParser implements Parser<SelectDeckCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectDeckCommand
     * and returns an SelectDeckCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectDeckCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectDeckCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE), pe);
        }
    }
}
