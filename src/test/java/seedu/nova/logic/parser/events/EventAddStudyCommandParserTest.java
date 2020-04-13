package seedu.nova.logic.parser.events;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.commons.core.Messages.MESSAGE_WRONG_TIME;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_1;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_2;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_3;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_4;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_5;
import static seedu.nova.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.nova.logic.commands.CommandTestUtil.STUDY_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.STUDY_DESC_2;
import static seedu.nova.logic.commands.CommandTestUtil.STUDY_TIME_DATE;
import static seedu.nova.logic.commands.CommandTestUtil.STUDY_VENUE;
import static seedu.nova.logic.commands.CommandTestUtil.TIME_DATE_2;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_STUDY_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_STUDY_TIME_DATE;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_STUDY_VENUE;
import static seedu.nova.logic.commands.CommandTestUtil.VENUE_2;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_TIME_FORMAT;
import static seedu.nova.testutil.TypicalEvents.STUDY_SESSION;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.sccommands.eventcommands.EventAddStudyCommand;
import seedu.nova.logic.parser.scparser.eventparsers.EventAddStudyCommandParser;
import seedu.nova.model.schedule.event.Event;

public class EventAddStudyCommandParserTest {

    private EventAddStudyCommandParser parser = new EventAddStudyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedStudySession = STUDY_SESSION;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDY_DESC + STUDY_VENUE + STUDY_TIME_DATE,
                new EventAddStudyCommand(expectedStudySession));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, STUDY_DESC_2 + STUDY_DESC + STUDY_VENUE + STUDY_TIME_DATE,
                new EventAddStudyCommand(expectedStudySession));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, STUDY_DESC + VENUE_2 + STUDY_VENUE + STUDY_TIME_DATE,
                new EventAddStudyCommand(expectedStudySession));

        // multiple date time - last date time accepted
        assertParseSuccess(parser, STUDY_DESC + STUDY_VENUE + TIME_DATE_2 + STUDY_TIME_DATE,
                new EventAddStudyCommand(expectedStudySession));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddStudyCommand.MESSAGE_USAGE);

        // missing desc prefix
        assertParseFailure(parser, VALID_STUDY_DESC + STUDY_VENUE + STUDY_TIME_DATE, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, STUDY_DESC + VALID_STUDY_VENUE + STUDY_TIME_DATE, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, STUDY_DESC + STUDY_VENUE + VALID_STUDY_TIME_DATE, expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, VALID_STUDY_DESC + VALID_STUDY_VENUE
                + VALID_STUDY_TIME_DATE, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddStudyCommand.MESSAGE_USAGE);

        // invalid time - end time is earlier than start
        assertParseFailure(parser, STUDY_DESC + STUDY_VENUE + INVALID_TIME_DATE_2, MESSAGE_WRONG_TIME);

        // invalid time - end time is missing
        assertParseFailure(parser, STUDY_DESC + STUDY_VENUE + INVALID_TIME_DATE_3, expectedMessage);

        // invalid date - date is missing
        assertParseFailure(parser, STUDY_DESC + STUDY_VENUE + INVALID_TIME_DATE_4, expectedMessage);

        // invalid time - does not exist
        assertParseFailure(parser, STUDY_DESC + STUDY_VENUE + INVALID_TIME_DATE_5, MESSAGE_INVALID_TIME_FORMAT);

        // invalid date - does not exist
        assertParseFailure(parser, STUDY_DESC + STUDY_VENUE + INVALID_TIME_DATE_1, MESSAGE_INVALID_DATE_FORMAT);

    }

}
