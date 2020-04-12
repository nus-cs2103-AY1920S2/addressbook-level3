package nasa.logic.parser.addcommandparser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.commands.CommandTestUtil.ACTIVITY_NAME_DESC_EXAM;
import static nasa.logic.commands.CommandTestUtil.DATE_DESC_TEST_FROM;
import static nasa.logic.commands.CommandTestUtil.DATE_DESC_TEST_TO;
import static nasa.logic.commands.CommandTestUtil.INVALID_ACTIVITY_NAME_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_DATE_FROM_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_DATE_TO_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_NOTES_DESC;
import static nasa.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.NOTES_DESC_TEST;
import static nasa.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_EXAM;
import static nasa.logic.commands.CommandTestUtil.VALID_DATE_TEST;
import static nasa.logic.commands.CommandTestUtil.VALID_DATE_TEST_2;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.addcommands.AddEventCommand;
import nasa.model.activity.Date;
import nasa.model.activity.Event;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.module.ModuleCode;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();
    private ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1231);

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        /*
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODULE_CODE_DESC_CS1231
            + ACTIVITY_NAME_DESC_EXAM + DATE_DESC_TEST_TO + DATE_DESC_TEST_FROM
            + NOTES_DESC_TEST,
            new AddEventCommand(new EventBuilder().build(), moduleCode));
         */
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing module code
        assertParseFailure(parser, ACTIVITY_NAME_DESC_EXAM + DATE_DESC_TEST_TO + DATE_DESC_TEST_FROM
             + NOTES_DESC_TEST, expectedMessage);

        // missing activity name
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + DATE_DESC_TEST_TO + DATE_DESC_TEST_FROM
             + NOTES_DESC_TEST, expectedMessage);

        // missing from-date
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_EXAM + DATE_DESC_TEST_TO
             + NOTES_DESC_TEST, expectedMessage);

        // missing to-date
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_EXAM + DATE_DESC_TEST_FROM
             + NOTES_DESC_TEST, expectedMessage);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // note field missing
        /*
        assertParseSuccess(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_EXAM
                + DATE_DESC_TEST_FROM + DATE_DESC_TEST_TO + PRIORITY_DESC_HIGH,
            new AddEventCommand(EventTemplate.getNoteFieldMissing(), moduleCode));
         */
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid module code
        assertParseFailure(parser, INVALID_MODULE_DESC + ACTIVITY_NAME_DESC_EXAM
            + DATE_DESC_TEST_TO + DATE_DESC_TEST_FROM + NOTES_DESC_TEST,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid activity name
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + INVALID_ACTIVITY_NAME_DESC
            + DATE_DESC_TEST_TO + DATE_DESC_TEST_FROM + NOTES_DESC_TEST,
                Name.MESSAGE_CONSTRAINTS);

        // invalid date-from
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_EXAM
            + INVALID_DATE_FROM_DESC + DATE_DESC_TEST_TO + NOTES_DESC_TEST,
                Date.MESSAGE_CONSTRAINTS);

        // invalid date-to
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_EXAM
            + INVALID_DATE_TO_DESC + DATE_DESC_TEST_FROM + NOTES_DESC_TEST,
                Date.MESSAGE_CONSTRAINTS);

        // invalid note
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_EXAM
            + DATE_DESC_TEST_FROM + DATE_DESC_TEST_TO + INVALID_NOTES_DESC,
                Note.MESSAGE_CONSTRAINTS);
    }

}

class EventTemplate {
    public static Event getNoteFieldMissing() {
        Event noteFieldMissing = new Event(new Name(VALID_ACTIVITY_NAME_EXAM),
            new Date(VALID_DATE_TEST), new Date(VALID_DATE_TEST_2));
        return noteFieldMissing;
    }
}
