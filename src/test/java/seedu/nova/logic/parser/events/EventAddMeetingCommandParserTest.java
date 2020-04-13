package seedu.nova.logic.parser.events;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.commons.core.Messages.MESSAGE_WRONG_TIME;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_1;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_2;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_3;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_4;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_5;
import static seedu.nova.logic.commands.CommandTestUtil.MEETING_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.MEETING_DESC_2;
import static seedu.nova.logic.commands.CommandTestUtil.MEETING_TIME_DATE;
import static seedu.nova.logic.commands.CommandTestUtil.MEETING_VENUE;
import static seedu.nova.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.nova.logic.commands.CommandTestUtil.TIME_DATE_2;

import static seedu.nova.logic.commands.CommandTestUtil.VALID_MEETING_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_MEETING_TIME_DATE;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_MEETING_VENUE;
import static seedu.nova.logic.commands.CommandTestUtil.VENUE_2;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_TIME_FORMAT;
import static seedu.nova.testutil.TypicalEvents.MEETING;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.sccommands.eventcommands.EventAddMeetingCommand;
import seedu.nova.logic.parser.scparser.eventparsers.EventAddMeetingCommandParser;
import seedu.nova.model.schedule.event.Event;

public class EventAddMeetingCommandParserTest {
    private EventAddMeetingCommandParser parser = new EventAddMeetingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedMeeting = MEETING;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MEETING_DESC + MEETING_VENUE + MEETING_TIME_DATE,
                new EventAddMeetingCommand(expectedMeeting));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, MEETING_DESC_2 + MEETING_DESC + MEETING_VENUE + MEETING_TIME_DATE,
                new EventAddMeetingCommand(expectedMeeting));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, MEETING_DESC + VENUE_2 + MEETING_VENUE + MEETING_TIME_DATE,
                new EventAddMeetingCommand(expectedMeeting));

        // multiple date time - last date time accepted
        assertParseSuccess(parser, MEETING_DESC + MEETING_VENUE + TIME_DATE_2 + MEETING_TIME_DATE,
                new EventAddMeetingCommand(expectedMeeting));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddMeetingCommand.MESSAGE_USAGE);

        // missing desc prefix
        assertParseFailure(parser, VALID_MEETING_DESC + MEETING_VENUE + MEETING_TIME_DATE, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, MEETING_DESC + VALID_MEETING_VENUE + MEETING_TIME_DATE, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, MEETING_DESC + MEETING_VENUE + VALID_MEETING_TIME_DATE, expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, VALID_MEETING_DESC + VALID_MEETING_VENUE
                + VALID_MEETING_TIME_DATE, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddMeetingCommand.MESSAGE_USAGE);

        // invalid time - end time is earlier than start
        assertParseFailure(parser, MEETING_DESC + MEETING_VENUE + INVALID_TIME_DATE_2, MESSAGE_WRONG_TIME);

        // invalid time - end time is missing
        assertParseFailure(parser, MEETING_DESC + MEETING_VENUE + INVALID_TIME_DATE_3, expectedMessage);

        // invalid date - date is missing
        assertParseFailure(parser, MEETING_DESC + MEETING_VENUE + INVALID_TIME_DATE_4, expectedMessage);

        // invalid time - does not exist
        assertParseFailure(parser, MEETING_DESC + MEETING_VENUE + INVALID_TIME_DATE_5, MESSAGE_INVALID_TIME_FORMAT);

        // invalid date - does not exist
        assertParseFailure(parser, MEETING_DESC + MEETING_VENUE + INVALID_TIME_DATE_1, MESSAGE_INVALID_DATE_FORMAT);

    }

}
