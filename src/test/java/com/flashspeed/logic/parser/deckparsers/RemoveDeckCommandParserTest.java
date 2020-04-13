package com.flashspeed.logic.parser.deckparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import com.flashspeed.logic.commands.deckcommands.RemoveDeckCommand;
import com.flashspeed.logic.parser.CommandParserTestUtil;

class RemoveDeckCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveDeckCommand.MESSAGE_USAGE);

    private RemoveDeckCommandParser parser = new RemoveDeckCommandParser();

    @Test
    void parse_invalidIndex_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }
}
