package com.flashspeed.logic.parser.cardparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import com.flashspeed.logic.commands.cardcommands.AddCardCommand;
import com.flashspeed.logic.parser.CommandParserTestUtil;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.deck.card.FrontFace;

class AddCardCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE);

    private AddCardCommandParser parser = new AddCardCommandParser();

    @Test
    void parse_noColon_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "front, back", MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_valid_success() {
        Card toAdd = new Card(new FrontFace("front value 1"), new BackFace("back value 1"));
        AddCardCommand expectedCommand = new AddCardCommand(toAdd);
        CommandParserTestUtil.assertParseSuccess(parser, "front value 1:back value 1", expectedCommand);
    }
}
