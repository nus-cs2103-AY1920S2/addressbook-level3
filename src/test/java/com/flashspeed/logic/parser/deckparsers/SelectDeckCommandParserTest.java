package com.flashspeed.logic.parser.deckparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import com.flashspeed.logic.commands.deckcommands.SelectDeckCommand;
import com.flashspeed.logic.parser.CommandParserTestUtil;

class SelectDeckCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeckCommand.MESSAGE_USAGE);

    private SelectDeckCommandParser parser = new SelectDeckCommandParser();

    @Test
    void parse_invalidIndex_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }
}
