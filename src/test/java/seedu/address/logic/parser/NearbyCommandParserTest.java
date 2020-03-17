package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NearbyCommand;

class NearbyCommandParserTest {
    private NearbyCommandParser parser = new NearbyCommandParser();

    @Test
    void parse_validArgs_returnsNearbyCommand() {
        Index postalSector = Index.fromOneBased(1);
        assertParseSuccess(parser, "1", new NearbyCommand(postalSector));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "NaN",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NearbyCommand.MESSAGE_USAGE));
    }
}
