//@@author fatin99

package tatracker.logic.parser.student;

import static tatracker.commons.core.Messages.MESSAGE_NOT_EDITED;
import static tatracker.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.GROUP_DESC_L08;
import static tatracker.logic.commands.CommandTestUtil.GROUP_DESC_T04;
import static tatracker.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static tatracker.logic.commands.CommandTestUtil.INVALID_MATRIC_DESC;
import static tatracker.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static tatracker.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static tatracker.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static tatracker.logic.commands.CommandTestUtil.MATRIC_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.MATRIC_DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.MODULE_DESC_CS2030;
import static tatracker.logic.commands.CommandTestUtil.MODULE_DESC_CS2040;
import static tatracker.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static tatracker.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_GROUP_L08;
import static tatracker.logic.commands.CommandTestUtil.VALID_GROUP_T04;
import static tatracker.logic.commands.CommandTestUtil.VALID_MATRIC_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_MODULE_CS2030;
import static tatracker.logic.commands.CommandTestUtil.VALID_MODULE_CS2040;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tatracker.logic.parser.Prefixes.TAG;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.student.EditStudentCommand;
import tatracker.logic.commands.student.EditStudentCommand.EditStudentDescriptor;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.tag.Tag;
import tatracker.testutil.student.EditStudentDescriptorBuilder;

public class EditStudentCommandParserTest {

    private static final String TAG_EMPTY = " " + TAG;

    private static final String MESSAGE_INVALID_FORMAT = Messages
            .getInvalidCommandMessage(EditStudentCommand.DETAILS.getUsage());

    private EditStudentCommandParser parser = new EditStudentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no module specified
        assertParseFailure(parser, GROUP_DESC_T04 + MATRIC_DESC_AMY
                + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no group specified
        assertParseFailure(parser, MODULE_DESC_CS2030 + MATRIC_DESC_AMY
                + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no matric specified
        assertParseFailure(parser, GROUP_DESC_T04 + MODULE_DESC_CS2030
                + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser,
                GROUP_DESC_T04 + MODULE_DESC_CS2030 + MATRIC_DESC_AMY, MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser,
                "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser,
                "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser,
                "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser,
                "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Group code and module cannot be invalid
        String compulsoryDesc = MATRIC_DESC_AMY + GROUP_DESC_L08 + MODULE_DESC_CS2040;

        // invalid matric
        assertParseFailure(parser,
                MODULE_DESC_CS2040 + GROUP_DESC_L08 + INVALID_MATRIC_DESC, Matric.MESSAGE_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, compulsoryDesc + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, compulsoryDesc + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, compulsoryDesc + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, compulsoryDesc + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid email
        assertParseFailure(parser, compulsoryDesc + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, compulsoryDesc + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Student} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser,
                compulsoryDesc + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                compulsoryDesc + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                compulsoryDesc + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                compulsoryDesc + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = MATRIC_DESC_AMY + MODULE_DESC_CS2030 + GROUP_DESC_T04 + NAME_DESC_AMY
                + PHONE_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_AMY + TAG_DESC_FRIEND;

        EditStudentDescriptor descriptor =
                new EditStudentDescriptorBuilder()
                        .withName(VALID_NAME_AMY)
                        .withPhone(VALID_PHONE_BOB)
                        .withEmail(VALID_EMAIL_AMY)
                        .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(
                new Matric(VALID_MATRIC_AMY), VALID_MODULE_CS2030, VALID_GROUP_T04, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = MATRIC_DESC_BOB + MODULE_DESC_CS2030 + GROUP_DESC_T04 + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditStudentDescriptor descriptor =
                new EditStudentDescriptorBuilder()
                        .withName(VALID_NAME_BOB)
                        .withPhone(VALID_PHONE_BOB)
                        .withEmail(VALID_EMAIL_AMY).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(
                new Matric(VALID_MATRIC_BOB), VALID_MODULE_CS2030, VALID_GROUP_T04, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Matric matric = new Matric(VALID_MATRIC_BOB);

        // name
        String userInput = MATRIC_DESC_BOB + GROUP_DESC_T04 + MODULE_DESC_CS2030 + NAME_DESC_AMY;
        EditStudentDescriptor descriptor =
                new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(
                matric, VALID_MODULE_CS2030, VALID_GROUP_T04, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = MATRIC_DESC_BOB + GROUP_DESC_T04 + MODULE_DESC_CS2030 + PHONE_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditStudentCommand(
                matric, VALID_MODULE_CS2030, VALID_GROUP_T04, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = MATRIC_DESC_BOB + GROUP_DESC_T04 + MODULE_DESC_CS2030 + EMAIL_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditStudentCommand(
                matric, VALID_MODULE_CS2030, VALID_GROUP_T04, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = MATRIC_DESC_BOB + GROUP_DESC_T04 + MODULE_DESC_CS2030 + TAG_DESC_FRIEND;
        descriptor = new EditStudentDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditStudentCommand(
                matric, VALID_MODULE_CS2030, VALID_GROUP_T04, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String firstInput = MATRIC_DESC_AMY + GROUP_DESC_L08 + MODULE_DESC_CS2040
                + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND;

        String lastInput = MATRIC_DESC_BOB + GROUP_DESC_T04 + MODULE_DESC_CS2030
                + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        String userInput = firstInput + firstInput + firstInput + lastInput;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(
                new Matric(VALID_MATRIC_BOB), VALID_MODULE_CS2030, VALID_GROUP_T04, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = MATRIC_DESC_AMY + GROUP_DESC_L08 + MODULE_DESC_CS2040
                + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditStudentDescriptor descriptor =
                new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(
                new Matric(VALID_MATRIC_AMY), VALID_MODULE_CS2040, VALID_GROUP_L08, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = MATRIC_DESC_AMY + GROUP_DESC_L08 + MODULE_DESC_CS2040
                + EMAIL_DESC_BOB + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        descriptor = new EditStudentDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();
        expectedCommand = new EditStudentCommand(
                new Matric(VALID_MATRIC_AMY), VALID_MODULE_CS2040, VALID_GROUP_L08, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        String userInput = MATRIC_DESC_AMY + GROUP_DESC_L08 + MODULE_DESC_CS2040 + TAG_EMPTY;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withTags().build();
        EditStudentCommand expectedCommand = new EditStudentCommand(
                new Matric(VALID_MATRIC_AMY), VALID_MODULE_CS2040, VALID_GROUP_L08, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
