package nasa.logic.parser.addcommandparser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.commands.CommandTestUtil.ACTIVITY_NAME_DESC_HWK;
import static nasa.logic.commands.CommandTestUtil.DATE_DESC_TEST;
import static nasa.logic.commands.CommandTestUtil.INVALID_ACTIVITY_NAME_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_NOTES_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static nasa.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.NOTES_DESC_TEST;
import static nasa.logic.commands.CommandTestUtil.PRIORITY_DESC_HIGH;
import static nasa.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_HWK;
import static nasa.logic.commands.CommandTestUtil.VALID_DATE_TEST;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_NOTES_TEST;
import static nasa.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static nasa.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.module.ModuleCode;

public class AddDeadlineCommandParserTest {

    private AddDeadlineCommandParser parser = new AddDeadlineCommandParser();
    private ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1231);

    @Test
    public void parse_allFieldPresent_success() {
        /*
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_HWK
            + DATE_DESC_TEST + NOTES_DESC_TEST
                + PRIORITY_DESC_HIGH, new AddDeadlineCommand(DeadlineBuilder.getAllFieldsPresent(), moduleCode));
         */
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        String expectedMessage = MESSAGE_INVALID_COMMAND_FORMAT;

        // missing moduleCode
        assertParseFailure(parser, ACTIVITY_NAME_DESC_HWK + DATE_DESC_TEST + NOTES_DESC_TEST
                + PRIORITY_DESC_HIGH, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_HWK + NOTES_DESC_TEST
                + PRIORITY_DESC_HIGH, expectedMessage);

        // missing activity name
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + DATE_DESC_TEST + NOTES_DESC_TEST
                + PRIORITY_DESC_HIGH, expectedMessage);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        /*
        // notes parameter missing
        assertParseSuccess(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_HWK + DATE_DESC_TEST
                + PRIORITY_DESC_HIGH, new AddDeadlineCommand(DeadlineBuilder.getNoteFieldMissing(),
                moduleCode));

        // priority parameter missing
        assertParseSuccess(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_HWK + DATE_DESC_TEST
                + NOTES_DESC_TEST, new AddDeadlineCommand(DeadlineBuilder.getPriorityFieldMissing(), moduleCode));
         */
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid module code
        assertParseFailure(parser, INVALID_MODULE_DESC + ACTIVITY_NAME_DESC_HWK
                + DATE_DESC_TEST + NOTES_DESC_TEST
                + PRIORITY_DESC_HIGH, ModuleCode.MESSAGE_CONSTRAINTS);

        // invalid activity name
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + INVALID_ACTIVITY_NAME_DESC
                + DATE_DESC_TEST + NOTES_DESC_TEST
                + PRIORITY_DESC_HIGH, Name.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_HWK
                + INVALID_DATE_DESC + NOTES_DESC_TEST
                + PRIORITY_DESC_HIGH, Date.MESSAGE_CONSTRAINTS);

        // invalid Notes
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_HWK
                + DATE_DESC_TEST + INVALID_NOTES_DESC
                + PRIORITY_DESC_HIGH, Note.MESSAGE_CONSTRAINTS);

        // invalid Priority
        assertParseFailure(parser, MODULE_CODE_DESC_CS1231 + ACTIVITY_NAME_DESC_HWK
                + DATE_DESC_TEST + NOTES_DESC_TEST
                + INVALID_PRIORITY_DESC, Priority.MESSAGE_CONSTRAINTS);
    }
}

class DeadlineBuilder {

    public static Deadline getAllFieldsPresent() {
        Deadline allFieldsPresent = new Deadline(new Name(VALID_ACTIVITY_NAME_HWK),
            new Date(VALID_DATE_TEST));
        allFieldsPresent.setNote(new Note(VALID_NOTES_TEST));
        allFieldsPresent.setPriority(new Priority(VALID_PRIORITY_HIGH));
        return allFieldsPresent;
    }

    public static Deadline getNoteFieldMissing() {
        Deadline noteFieldMissing = new Deadline(new Name(VALID_ACTIVITY_NAME_HWK),
            new Date(VALID_DATE_TEST));
        noteFieldMissing.setPriority(new Priority(VALID_PRIORITY_HIGH));
        return noteFieldMissing;
    }

    public static Deadline getPriorityFieldMissing() {
        Deadline priorityFieldMissing = new Deadline(new Name(VALID_ACTIVITY_NAME_HWK),
            new Date(VALID_DATE_TEST));
        priorityFieldMissing.setNote(new Note(VALID_NOTES_TEST));
        return priorityFieldMissing;
    }
}
