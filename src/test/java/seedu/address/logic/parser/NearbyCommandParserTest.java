package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NearbyCommand;

class NearbyCommandParserTest {
    private NearbyCommandParser parser = new NearbyCommandParser();

    @Test
    void parse_validArgs_returnsNearbyCommand() {
        assertParseSuccess(parser, "1", new NearbyCommand("1"));
        assertParseSuccess(parser, "   1   ", new NearbyCommand("1"));
        assertParseSuccess(parser, "central", new NearbyCommand("central"));
        assertParseSuccess(parser, "east", new NearbyCommand("east"));
        assertParseSuccess(parser, "north-east", new NearbyCommand("north-east"));
        assertParseSuccess(parser, "west", new NearbyCommand("west"));
        assertParseSuccess(parser, "north", new NearbyCommand("north"));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "central 100",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NearbyCommand.MESSAGE_USAGE));
    }
}
