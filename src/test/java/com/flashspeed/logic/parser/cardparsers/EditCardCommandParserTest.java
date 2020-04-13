package com.flashspeed.logic.parser.cardparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.cardcommands.EditCardCommand;
import com.flashspeed.logic.parser.CommandParserTestUtil;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.FrontFace;

class EditCardCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE);

    private Index idxOneFromOneBased = Index.fromOneBased(1);
    private FrontFace ff = new FrontFace("front");
    private BackFace bf = new BackFace("back");
    private FrontFace emptyFront = new FrontFace("");
    private BackFace emptyBack = new BackFace("");

    private EditCardCommandParser parser = new EditCardCommandParser();

    @Test
    void parse_noIndex_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "front:back", MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_noFrontAndBack_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1 :", MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_noColon_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1 front, back", MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_invalidIndex_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_withFrontOnly_success() {
        EditCardCommand expectedCommand = new EditCardCommand(idxOneFromOneBased, ff, emptyBack);
        CommandParserTestUtil.assertParseSuccess(parser, "1 front:", expectedCommand);
    }

    @Test
    void parse_withBackOnly_success() {
        EditCardCommand expectedCommand = new EditCardCommand(idxOneFromOneBased, emptyFront, bf);
        CommandParserTestUtil.assertParseSuccess(parser, "1 :back", expectedCommand);
    }

    @Test
    void parse_withFrontAndBack_success() {
        EditCardCommand expectedCommand = new EditCardCommand(idxOneFromOneBased, ff, bf);
        CommandParserTestUtil.assertParseSuccess(parser, "1 front:back", expectedCommand);
    }
}
