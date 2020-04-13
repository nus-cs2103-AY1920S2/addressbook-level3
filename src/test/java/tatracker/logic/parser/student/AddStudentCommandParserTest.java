//@@author fatin99

package tatracker.logic.parser.student;

import static tatracker.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.GROUP_DESC_T04;
import static tatracker.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static tatracker.logic.commands.CommandTestUtil.INVALID_MATRIC_DESC;
import static tatracker.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static tatracker.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static tatracker.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static tatracker.logic.commands.CommandTestUtil.MATRIC_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.MATRIC_DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.MODULE_DESC_CS2030;
import static tatracker.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static tatracker.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tatracker.logic.commands.CommandTestUtil.RATING_DESC_BOB;
import static tatracker.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static tatracker.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_GROUP_T04;
import static tatracker.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_MODULE_CS2030;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tatracker.testutil.student.TypicalStudents.AMY;
import static tatracker.testutil.student.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.student.AddStudentCommand;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Student;
import tatracker.model.tag.Tag;
import tatracker.testutil.student.StudentBuilder;

public class AddStudentCommandParserTest {

    private static final Module MODULE = new Module(VALID_MODULE_CS2030);
    private static final Group GROUP = new Group(VALID_GROUP_T04);

    private AddStudentCommandParser parser = new AddStudentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(BOB)
                .withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        StringBuilder command = new StringBuilder()
                .append(PREAMBLE_WHITESPACE)
                .append(MATRIC_DESC_BOB)
                .append(NAME_DESC_BOB)
                .append(PHONE_DESC_BOB)
                .append(EMAIL_DESC_BOB)
                .append(RATING_DESC_BOB)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseSuccess(parser, command.toString(), new AddStudentCommand(expectedStudent,
                GROUP.getIdentifier(), MODULE.getIdentifier()));


        // multiple matric numbers - last matric number accepted
        command = new StringBuilder()
                .append(MATRIC_DESC_AMY)
                .append(MATRIC_DESC_BOB)
                .append(NAME_DESC_BOB)
                .append(PHONE_DESC_BOB)
                .append(EMAIL_DESC_BOB)
                .append(RATING_DESC_BOB)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseSuccess(parser, command.toString(), new AddStudentCommand(expectedStudent,
                GROUP.getIdentifier(), MODULE.getIdentifier()));


        // multiple names - last name accepted
        command = new StringBuilder()
                .append(MATRIC_DESC_BOB)
                .append(NAME_DESC_AMY)
                .append(NAME_DESC_BOB)
                .append(PHONE_DESC_BOB)
                .append(EMAIL_DESC_BOB)
                .append(RATING_DESC_BOB)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseSuccess(parser, command.toString(), new AddStudentCommand(expectedStudent,
                GROUP.getIdentifier(), MODULE.getIdentifier()));

        // multiple phones - last phone accepted
        command = new StringBuilder()
                .append(MATRIC_DESC_BOB)
                .append(NAME_DESC_BOB)
                .append(PHONE_DESC_AMY)
                .append(PHONE_DESC_BOB)
                .append(EMAIL_DESC_BOB)
                .append(RATING_DESC_BOB)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseSuccess(parser, command.toString(), new AddStudentCommand(expectedStudent,
                GROUP.getIdentifier(), MODULE.getIdentifier()));

        // multiple emails - last email accepted
        command = new StringBuilder()
                .append(MATRIC_DESC_BOB)
                .append(NAME_DESC_BOB)
                .append(PHONE_DESC_BOB)
                .append(EMAIL_DESC_AMY)
                .append(EMAIL_DESC_BOB)
                .append(RATING_DESC_BOB)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseSuccess(parser, command.toString(), new AddStudentCommand(expectedStudent,
                GROUP.getIdentifier(), MODULE.getIdentifier()));

        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new StudentBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();

        command = new StringBuilder()
                .append(MATRIC_DESC_BOB)
                .append(NAME_DESC_BOB)
                .append(PHONE_DESC_BOB)
                .append(EMAIL_DESC_BOB)
                .append(RATING_DESC_BOB)
                .append(TAG_DESC_FRIEND)
                .append(TAG_DESC_HUSBAND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseSuccess(parser, command.toString(),
                new AddStudentCommand(expectedStudentMultipleTags,
                        GROUP.getIdentifier(), MODULE.getIdentifier()));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no optional fields
        Student expectedStudent = new StudentBuilder(AMY)
                .withPhone("")
                .withEmail("")
                .withRating(3)
                .withTags().build();

        StringBuilder command = new StringBuilder()
                .append(MATRIC_DESC_AMY)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04)
                .append(NAME_DESC_AMY);

        assertParseSuccess(parser, command.toString(), new AddStudentCommand(expectedStudent,
                GROUP.getIdentifier(), MODULE.getIdentifier()));

        // no tags
        expectedStudent = new StudentBuilder(AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withRating(3)
                .withTags().build();

        command = new StringBuilder()
                .append(MATRIC_DESC_AMY)
                .append(PHONE_DESC_AMY)
                .append(EMAIL_DESC_AMY)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04)
                .append(NAME_DESC_AMY);

        assertParseSuccess(parser, command.toString(), new AddStudentCommand(expectedStudent,
                GROUP.getIdentifier(), MODULE.getIdentifier()));

        // no email
        expectedStudent = new StudentBuilder(AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail("")
                .withRating(3)
                .withTags(VALID_TAG_FRIEND).build();

        command = new StringBuilder()
                .append(MATRIC_DESC_AMY)
                .append(MODULE_DESC_CS2030)
                .append(PHONE_DESC_AMY)
                .append(TAG_DESC_FRIEND)
                .append(GROUP_DESC_T04)
                .append(NAME_DESC_AMY);

        assertParseSuccess(parser, command.toString(), new AddStudentCommand(expectedStudent,
                GROUP.getIdentifier(), MODULE.getIdentifier()));

        // no phone
        expectedStudent = new StudentBuilder(AMY)
                .withPhone("")
                .withEmail(VALID_EMAIL_AMY)
                .withRating(3)
                .withTags(VALID_TAG_FRIEND).build();

        command = new StringBuilder()
                .append(MATRIC_DESC_AMY)
                .append(MODULE_DESC_CS2030)
                .append(EMAIL_DESC_AMY)
                .append(TAG_DESC_FRIEND)
                .append(GROUP_DESC_T04)
                .append(NAME_DESC_AMY);

        assertParseSuccess(parser, command.toString(), new AddStudentCommand(expectedStudent,
                GROUP.getIdentifier(), MODULE.getIdentifier()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = Messages.getInvalidCommandMessage(AddStudentCommand.DETAILS.getUsage());

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + MATRIC_DESC_BOB,
                expectedMessage);

        // missing matric prefix
        assertParseFailure(parser, NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + VALID_MATRIC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB
                        + VALID_PHONE_BOB + VALID_EMAIL_BOB
                        + MATRIC_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid matric
        StringBuilder command = new StringBuilder()
                .append(INVALID_MATRIC_DESC)
                .append(NAME_DESC_BOB)
                .append(PHONE_DESC_BOB)
                .append(EMAIL_DESC_BOB)
                .append(TAG_DESC_HUSBAND)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseFailure(parser, command.toString(), Matric.MESSAGE_CONSTRAINTS);

        // invalid name
        command = new StringBuilder()
                .append(MATRIC_DESC_BOB)
                .append(INVALID_NAME_DESC)
                .append(PHONE_DESC_BOB)
                .append(EMAIL_DESC_BOB)
                .append(TAG_DESC_HUSBAND)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseFailure(parser, command.toString(), Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        command = new StringBuilder()
                .append(MATRIC_DESC_BOB)
                .append(NAME_DESC_BOB)
                .append(INVALID_PHONE_DESC)
                .append(EMAIL_DESC_BOB)
                .append(TAG_DESC_HUSBAND)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseFailure(parser, command.toString(), Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        command = new StringBuilder()
                .append(MATRIC_DESC_BOB)
                .append(NAME_DESC_BOB)
                .append(PHONE_DESC_BOB)
                .append(INVALID_EMAIL_DESC)
                .append(TAG_DESC_HUSBAND)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseFailure(parser, command.toString(), Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        command = new StringBuilder()
                .append(MATRIC_DESC_BOB)
                .append(NAME_DESC_BOB)
                .append(PHONE_DESC_BOB)
                .append(EMAIL_DESC_BOB)
                .append(INVALID_TAG_DESC)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseFailure(parser, command.toString(), Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        command = new StringBuilder()
                .append(MATRIC_DESC_BOB)
                .append(INVALID_NAME_DESC)
                .append(INVALID_PHONE_DESC)
                .append(EMAIL_DESC_BOB)
                .append(TAG_DESC_HUSBAND)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseFailure(parser, command.toString(), Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        // invalid email
        command = new StringBuilder()
                .append(PREAMBLE_NON_EMPTY)
                .append(MATRIC_DESC_BOB)
                .append(NAME_DESC_BOB)
                .append(PHONE_DESC_BOB)
                .append(EMAIL_DESC_BOB)
                .append(TAG_DESC_HUSBAND)
                .append(TAG_DESC_FRIEND)
                .append(MODULE_DESC_CS2030)
                .append(GROUP_DESC_T04);

        assertParseFailure(parser,
                command.toString(),
                Messages.getInvalidCommandMessage(AddStudentCommand.DETAILS.getUsage()));
    }
}
