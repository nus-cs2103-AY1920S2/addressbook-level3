package seedu.nova.logic.parser.events;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.commons.core.Messages.MESSAGE_WRONG_TIME;
import static seedu.nova.logic.commands.CommandTestUtil.CONSULTATION_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.CONSULTATION_DESC_2;
import static seedu.nova.logic.commands.CommandTestUtil.CONS_TIME_DATE;
import static seedu.nova.logic.commands.CommandTestUtil.CONS_VENUE;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_1;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_2;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_3;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_4;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TIME_DATE_5;
import static seedu.nova.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;

import static seedu.nova.logic.commands.CommandTestUtil.TIME_DATE_2;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_CONSULTATION_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_CONS_TIME_DATE;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_CONS_VENUE;
import static seedu.nova.logic.commands.CommandTestUtil.VENUE_2;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.nova.logic.parser.ParserUtil.MESSAGE_INVALID_TIME_FORMAT;
import static seedu.nova.testutil.TypicalEvents.CONSULTATION;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.sccommands.eventcommands.EventAddConsultationCommand;
import seedu.nova.logic.parser.scparser.eventparsers.EventAddConsultationCommandParser;
import seedu.nova.model.schedule.event.Event;

public class EventAddConsultationCommandParserTest {
    private EventAddConsultationCommandParser parser = new EventAddConsultationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedConsultation = CONSULTATION;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CONSULTATION_DESC + CONS_VENUE + CONS_TIME_DATE,
                new EventAddConsultationCommand(expectedConsultation));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, CONSULTATION_DESC_2 + CONSULTATION_DESC + CONS_VENUE + CONS_TIME_DATE,
                new EventAddConsultationCommand(expectedConsultation));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, CONSULTATION_DESC + VENUE_2 + CONS_VENUE + CONS_TIME_DATE,
                new EventAddConsultationCommand(expectedConsultation));

        // multiple date time - last date time accepted
        assertParseSuccess(parser, CONSULTATION_DESC + CONS_VENUE + TIME_DATE_2 + CONS_TIME_DATE,
                new EventAddConsultationCommand(expectedConsultation));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddConsultationCommand.MESSAGE_USAGE);

        // missing desc prefix
        assertParseFailure(parser, VALID_CONSULTATION_DESC + CONS_VENUE + CONS_TIME_DATE, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, CONSULTATION_DESC + VALID_CONS_VENUE + CONS_TIME_DATE, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, CONSULTATION_DESC + CONS_VENUE + VALID_CONS_TIME_DATE, expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, VALID_CONSULTATION_DESC + VALID_CONS_VENUE + VALID_CONS_TIME_DATE, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EventAddConsultationCommand.MESSAGE_USAGE);

        // invalid time - end time is earlier than start
        assertParseFailure(parser, CONSULTATION_DESC + CONS_VENUE + INVALID_TIME_DATE_2, MESSAGE_WRONG_TIME);

        // invalid time - end time is missing
        assertParseFailure(parser, CONSULTATION_DESC + CONS_VENUE + INVALID_TIME_DATE_3, expectedMessage);

        // invalid date - date is missing
        assertParseFailure(parser, CONSULTATION_DESC + CONS_VENUE + INVALID_TIME_DATE_4, expectedMessage);

        // invalid time - does not exist
        assertParseFailure(parser, CONSULTATION_DESC + CONS_VENUE + INVALID_TIME_DATE_5, MESSAGE_INVALID_TIME_FORMAT);

        // invalid date - does not exist
        assertParseFailure(parser, CONSULTATION_DESC + CONS_VENUE + INVALID_TIME_DATE_1, MESSAGE_INVALID_DATE_FORMAT);

    }

}
