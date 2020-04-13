package seedu.nova.logic.parser.events;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.nova.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.sccommands.eventcommands.EventDeleteCommand;
import seedu.nova.logic.parser.scparser.eventparsers.EventDeleteCommandParser;

public class EventDeleteCommandParserTest {

    private static final LocalDate VALID_DATE = LocalDate.parse("2020-04-10");

    private EventDeleteCommandParser parser = new EventDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, " t\\2020-04-10 i\\1",
                new EventDeleteCommand(VALID_DATE, INDEX_FIRST_EVENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " t\\2020-04-10", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventDeleteCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " i\\1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventDeleteCommand.MESSAGE_USAGE));
    }

}
