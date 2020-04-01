package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.commands.CommandTestUtil.ACTIVITY_NAME_DESC_EXAM;
import static nasa.logic.commands.CommandTestUtil.ACTIVITY_NAME_DESC_HWK;
import static nasa.logic.commands.CommandTestUtil.DATE_DESC_TEST;
import static nasa.logic.commands.CommandTestUtil.DATE_DESC_TEST_2;
import static nasa.logic.commands.CommandTestUtil.INVALID_ACTIVITY_NAME_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_NOTES_DESC;
import static nasa.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static nasa.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS2030;
import static nasa.logic.commands.CommandTestUtil.NOTES_DESC_TEST;
import static nasa.logic.commands.CommandTestUtil.NOTES_DESC_TEST_2;
import static nasa.logic.commands.CommandTestUtil.PRIORITY_DESC_HIGH;
import static nasa.logic.commands.CommandTestUtil.PRIORITY_DESC_LOW;
import static nasa.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_EXAM;
import static nasa.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_HWK;
import static nasa.logic.commands.CommandTestUtil.VALID_DATE_TEST;
import static nasa.logic.commands.CommandTestUtil.VALID_DATE_TEST_2;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2030;
import static nasa.logic.commands.CommandTestUtil.VALID_NOTES_TEST;
import static nasa.logic.commands.CommandTestUtil.VALID_NOTES_TEST_2;
import static nasa.logic.commands.CommandTestUtil.VALID_PRIORITY_HIGH;
import static nasa.logic.commands.CommandTestUtil.VALID_PRIORITY_LOW;
import static nasa.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nasa.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static nasa.testutil.TypicalIndexes.INDEX_SECOND_ACTIVITY;
import static nasa.testutil.TypicalIndexes.INDEX_THIRD_ACTIVITY;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.logic.commands.EditActivityCommand;
import nasa.logic.commands.EditActivityCommand.EditActivityDescriptor;
import nasa.model.activity.Date;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.module.ModuleCode;
import nasa.testutil.EditActivityDescriptorBuilder;

public class EditActivityCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditActivityCommand.MESSAGE_USAGE);

    private EditActivityCommandParser parser = new EditActivityCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_MODULE_CODE_CS1231 + ACTIVITY_NAME_DESC_EXAM, MESSAGE_INVALID_FORMAT);

        // no module code specified
        assertParseFailure(parser, "1" + ACTIVITY_NAME_DESC_EXAM, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2030, EditActivityCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + MODULE_CODE_DESC_CS2030 + ACTIVITY_NAME_DESC_EXAM,
                 MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + MODULE_CODE_DESC_CS1231 + PRIORITY_DESC_HIGH, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 " + MODULE_CODE_DESC_CS2030 + " i/ string", MESSAGE_INVALID_FORMAT);

        // invalid target module code
        assertParseFailure(parser, "1" + INVALID_MODULE_DESC + VALID_ACTIVITY_NAME_EXAM,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2030 + INVALID_ACTIVITY_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2030 + INVALID_DATE_DESC,
                Date.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2030 + INVALID_NOTES_DESC,
                Note.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2030 + INVALID_PRIORITY_DESC,
                Priority.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid priority followed by valid activity name
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2030 + INVALID_PRIORITY_DESC
                + VALID_ACTIVITY_NAME_EXAM, Priority.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2030 + VALID_PRIORITY_HIGH + INVALID_PRIORITY_DESC,
                Priority.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + MODULE_CODE_DESC_CS2030 + INVALID_PRIORITY_DESC + INVALID_NOTES_DESC
                        + VALID_ACTIVITY_NAME_EXAM + VALID_DATE_TEST, Priority.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ACTIVITY;
        ModuleCode targetModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2030);

        String userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2030 + DATE_DESC_TEST + NOTES_DESC_TEST
                + PRIORITY_DESC_HIGH + ACTIVITY_NAME_DESC_EXAM;

        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder()
                .withDate(VALID_DATE_TEST).withNote(VALID_NOTES_TEST)
                .withPriority(VALID_PRIORITY_HIGH).withName(VALID_ACTIVITY_NAME_EXAM).build();

        EditActivityCommand expectedCommand = new EditActivityCommand(targetIndex, targetModuleCode, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ACTIVITY;
        ModuleCode targetModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2030);

        String userInput = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2030 + DATE_DESC_TEST + NOTES_DESC_TEST;

        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withDate(VALID_DATE_TEST)
                .withNote(VALID_NOTES_TEST).build();

        EditActivityCommand expectedCommand = new EditActivityCommand(targetIndex, targetModuleCode, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_oneFieldSpecified_success() {

        Index targetIndex = INDEX_THIRD_ACTIVITY;
        ModuleCode targetModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2030);
        String preamble = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2030;

        // name
        String userInput = preamble + ACTIVITY_NAME_DESC_HWK;
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withName(VALID_ACTIVITY_NAME_HWK)
                .build();
        EditActivityCommand expectedCommand = new EditActivityCommand(targetIndex, targetModuleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = preamble + DATE_DESC_TEST;
        descriptor = new EditActivityDescriptorBuilder().withDate(VALID_DATE_TEST).build();
        expectedCommand = new EditActivityCommand(targetIndex, targetModuleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // note
        userInput = preamble + NOTES_DESC_TEST;
        descriptor = new EditActivityDescriptorBuilder().withNote(VALID_NOTES_TEST).build();
        expectedCommand = new EditActivityCommand(targetIndex, targetModuleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // priority
        userInput = preamble + PRIORITY_DESC_HIGH;
        descriptor = new EditActivityDescriptorBuilder().withPriority(VALID_PRIORITY_HIGH).build();
        expectedCommand = new EditActivityCommand(targetIndex, targetModuleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_ACTIVITY;
        ModuleCode targetModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2030);
        String preamble = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2030;

        String userInput = preamble + ACTIVITY_NAME_DESC_HWK + DATE_DESC_TEST + NOTES_DESC_TEST + PRIORITY_DESC_HIGH
                + ACTIVITY_NAME_DESC_EXAM + DATE_DESC_TEST_2 + NOTES_DESC_TEST_2 + PRIORITY_DESC_LOW;

        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withName(VALID_ACTIVITY_NAME_EXAM)
                .withDate(VALID_DATE_TEST_2).withNote(VALID_NOTES_TEST_2).withPriority(VALID_PRIORITY_LOW)
                .build();
        EditActivityCommand expectedCommand = new EditActivityCommand(targetIndex, targetModuleCode, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ACTIVITY;
        ModuleCode targetModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2030);
        String preamble = targetIndex.getOneBased() + MODULE_CODE_DESC_CS2030;

        String userInput = preamble + INVALID_ACTIVITY_NAME_DESC + ACTIVITY_NAME_DESC_EXAM;
        EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder().withName(VALID_ACTIVITY_NAME_EXAM)
                .build();
        EditActivityCommand expectedCommand = new EditActivityCommand(targetIndex, targetModuleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = preamble + INVALID_NOTES_DESC + ACTIVITY_NAME_DESC_EXAM + NOTES_DESC_TEST + DATE_DESC_TEST
                + PRIORITY_DESC_LOW;
        descriptor = new EditActivityDescriptorBuilder().withName(VALID_ACTIVITY_NAME_EXAM).withDate(VALID_DATE_TEST)
                .withNote(VALID_NOTES_TEST).withPriority(VALID_PRIORITY_LOW).build();

        expectedCommand = new EditActivityCommand(targetIndex, targetModuleCode, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}

