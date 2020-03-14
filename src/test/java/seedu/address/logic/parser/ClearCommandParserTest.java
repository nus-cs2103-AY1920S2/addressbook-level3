package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;

class ClearCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_invalidFlag_throwsParseException() {

        assertParseFailure(parser, "-h", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));
    }
}
