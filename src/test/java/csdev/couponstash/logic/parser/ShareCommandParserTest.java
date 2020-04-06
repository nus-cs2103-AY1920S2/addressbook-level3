package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.ShareCommand;

import csdev.couponstash.testutil.TypicalIndexes;

class ShareCommandParserTest {
    private ShareCommandParser parser = new ShareCommandParser();

    @Test
    public void parse_validArgs() {
        CommandParserTestUtil.assertParseSuccess(parser, "1",
                new ShareCommand(TypicalIndexes.INDEX_FIRST_COUPON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(
                parser,
                "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE)
        );
    }
}
