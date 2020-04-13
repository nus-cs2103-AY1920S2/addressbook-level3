package seedu.nova.logic.parser.events;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.commons.core.Messages.MESSAGE_WRONG_TIME;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_1;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_2;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_3;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_4;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_5;
import static seedu.nova.logic.commands.CommandTestUtil.LESSON_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.LESSON_DESC_2;
import static seedu.nova.logic.commands.CommandTestUtil.LESSON_TIME;
import static seedu.nova.logic.commands.CommandTestUtil.LESSON_VENUE;
import static seedu.nova.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.nova.logic.commands.CommandTestUtil.TIME_2;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_LESSON_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_LESSON_TIME;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_LESSON_VENUE;
import static seedu.nova.logic.commands.CommandTestUtil.VENUE_2;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_DAY_FORMAT;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_TIME_FORMAT;
import static seedu.nova.testutil.TypicalEvents.LESSON_WEEK1;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.sccommands.eventcommands.EventAddLessonCommand;
import seedu.nova.logic.parser.scparser.eventparsers.EventAddLessonCommandParser;
import seedu.nova.model.schedule.event.Event;

public class EventAddLessonCommandParserTest {

    private EventAddLessonCommandParser parser = new EventAddLessonCommandParser();


    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedLesson = LESSON_WEEK1;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LESSON_DESC + LESSON_VENUE + LESSON_TIME,
                new EventAddLessonCommand(expectedLesson));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, LESSON_DESC_2 + LESSON_DESC + LESSON_VENUE + LESSON_TIME,
                new EventAddLessonCommand(expectedLesson));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, LESSON_DESC + VENUE_2 + LESSON_VENUE + LESSON_TIME,
                new EventAddLessonCommand(expectedLesson));

        // multiple time - last time accepted
        assertParseSuccess(parser, LESSON_DESC + LESSON_VENUE + TIME_2 + LESSON_TIME,
                new EventAddLessonCommand(expectedLesson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddLessonCommand.MESSAGE_USAGE);

        // missing desc prefix
        assertParseFailure(parser, VALID_LESSON_DESC + LESSON_VENUE + LESSON_TIME, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, LESSON_DESC + VALID_LESSON_VENUE + LESSON_TIME, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, LESSON_DESC + LESSON_VENUE + VALID_LESSON_TIME, expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, VALID_LESSON_DESC + VALID_LESSON_VENUE
                + VALID_LESSON_TIME, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddLessonCommand.MESSAGE_USAGE);

        // invalid time - end time is earlier than start
        assertParseFailure(parser, LESSON_DESC + LESSON_VENUE + INVALID_TIME_2, MESSAGE_WRONG_TIME);

        // invalid time - end time is missing
        assertParseFailure(parser, LESSON_DESC + LESSON_VENUE + INVALID_TIME_3, expectedMessage);

        // invalid date - day is missing
        assertParseFailure(parser, LESSON_DESC + LESSON_VENUE + INVALID_TIME_4, expectedMessage);

        // invalid time - does not exist
        assertParseFailure(parser, LESSON_DESC + LESSON_VENUE + INVALID_TIME_5, MESSAGE_INVALID_TIME_FORMAT);

        // invalid day - does not exist
        assertParseFailure(parser, LESSON_DESC + LESSON_VENUE + INVALID_TIME_1, MESSAGE_INVALID_DAY_FORMAT);

    }

}
