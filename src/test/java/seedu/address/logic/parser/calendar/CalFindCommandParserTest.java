package seedu.address.logic.parser.calendar;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_SPACES;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.calendar.CalFindCommand;

public class CalFindCommandParserTest {
    private CalFindCommandParser parser = new CalFindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalFindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, EMPTY_SPACES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsCalFindCommand() {
        CalFindCommand expectedCalFindCommand = new CalFindCommand();
        //no leading and trailing whitespaces
        assertParseSuccess(parser, "empty", expectedCalFindCommand);

        //leading and trailing whitespaces
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "empty" + PREAMBLE_WHITESPACE,
                expectedCalFindCommand);

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //invalid word
        assertParseFailure(parser, "hello",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalFindCommand.MESSAGE_USAGE));
    }
}
