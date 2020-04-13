package com.flashspeed.logic.parser.cardparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.cardcommands.DeleteCardCommand;
import com.flashspeed.logic.parser.CommandParserTestUtil;

class DeleteCardCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCardCommand.MESSAGE_USAGE);

    private DeleteCardCommandParser parser = new DeleteCardCommandParser();

    @Test
    void parse_invalidIndex_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_valid_success() {
        DeleteCardCommand expectedCommand = new DeleteCardCommand(Index.fromOneBased(1));
        CommandParserTestUtil.assertParseSuccess(parser, "1", expectedCommand);
    }
}
