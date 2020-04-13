package com.flashspeed.logic.parser.deckparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import com.flashspeed.logic.commands.deckcommands.CreateDeckCommand;
import com.flashspeed.logic.parser.CommandParserTestUtil;

class CreateDeckCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateDeckCommand.MESSAGE_USAGE);

    private CreateDeckCommandParser parser = new CreateDeckCommandParser();

    @Test
    void parse_blankName_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        CommandParserTestUtil.assertParseFailure(parser, "        ", MESSAGE_INVALID_FORMAT);
    }
}
