package com.flashspeed.logic.parser.deckparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import com.flashspeed.logic.commands.deckcommands.RenameDeckCommand;
import com.flashspeed.logic.parser.CommandParserTestUtil;

class RenameDeckCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameDeckCommand.MESSAGE_USAGE);

    private RenameDeckCommandParser parser = new RenameDeckCommandParser();

    @Test
    void parse_blankName_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1  ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_invalidIndex_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }
}
