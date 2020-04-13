package com.flashspeed.logic.parser.gameparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import com.flashspeed.logic.commands.gamecommands.PlayCommand;
import com.flashspeed.logic.parser.CommandParserTestUtil;

class PlayCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PlayCommand.MESSAGE_USAGE);

    private PlayCommandParser parser = new PlayCommandParser();

    @Test
    void parse_invalidIndex_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }
}
