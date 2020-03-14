package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class ClearCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_invalidFlag_throwsParseException() {

        assertParseFailure(parser, "-h", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withoutFlag_returnNull() {
        try {
            ClearCommand actualCommand = parser.parse("");
            assertNull(actualCommand.getFlag());
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_correctFlag_returnCorrectCommand() {

        try {
            ClearCommand actualCommand = parser.parse(FLAG_FORCE_CLEAR.toString());
            ClearCommand expectedCommand = new ClearCommand(FLAG_FORCE_CLEAR.toString());

            assertEquals(actualCommand, expectedCommand);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
}
