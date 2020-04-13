package seedu.address.logic.parser.consult;

import static seedu.address.commons.core.Messages.MESSAGE_CONSULT_DIFFERENT_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PLACE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PLACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PLACE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_INDEX_INPUT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE_TIME;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consults.AddConsultCommand;
import seedu.address.logic.parser.consults.AddConsultCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Location;
import seedu.address.model.event.consult.Consult;
import seedu.address.testutil.ConsultBuilder;

public class AddConsultCommandParserTest {
    private AddConsultCommandParser parser = new AddConsultCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Consult expectedConsult = new ConsultBuilder().build();
        Index index = Index.fromZeroBased(VALID_INDEX);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_STUDENT_INDEX_INPUT + VALID_START_TIME_INPUT
            + VALID_END_TIME_INPUT + VALID_PLACE_INPUT, new AddConsultCommand(index, expectedConsult));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddConsultCommand.MESSAGE_USAGE);

        // missing index prefix
        assertParseFailure(parser, VALID_INDEX + VALID_START_TIME_INPUT + VALID_END_TIME_INPUT
            + VALID_PLACE_INPUT, expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, VALID_STUDENT_INDEX_INPUT + VALID_START_TIME + VALID_END_TIME_INPUT
            + VALID_PLACE_INPUT, expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, VALID_STUDENT_INDEX_INPUT + VALID_START_TIME_INPUT + VALID_END_TIME
            + VALID_PLACE_INPUT, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_INDEX + VALID_START_TIME + VALID_END_TIME + VALID_PLACE,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid begin date time
        assertParseFailure(parser, VALID_STUDENT_INDEX_INPUT + INVALID_START_TIME_INPUT + VALID_END_TIME_INPUT
            + VALID_PLACE_INPUT, MESSAGE_INVALID_DATE_TIME);

        // invalid end date time, different from start date time
        assertParseFailure(parser, VALID_STUDENT_INDEX_INPUT + VALID_START_TIME_INPUT + INVALID_END_TIME_INPUT
            + VALID_PLACE_INPUT, MESSAGE_CONSULT_DIFFERENT_DATE);

        // invalid place
        assertParseFailure(parser, VALID_STUDENT_INDEX_INPUT + VALID_START_TIME_INPUT + VALID_END_TIME_INPUT
            + INVALID_PLACE_INPUT, Location.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_START_TIME_INPUT + INVALID_PLACE_INPUT
                + VALID_END_TIME_INPUT + VALID_STUDENT_INDEX_INPUT, MESSAGE_INVALID_DATE_TIME);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_STUDENT_INDEX_INPUT + VALID_START_TIME_INPUT
                + VALID_END_TIME_INPUT + VALID_PLACE_INPUT,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddConsultCommand.MESSAGE_USAGE));
    }
}
