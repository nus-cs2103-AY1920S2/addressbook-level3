package seedu.nova.logic.parser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.TAG_DESC_CLASSMATE;
import static seedu.nova.logic.commands.CommandTestUtil.TAG_DESC_TEAMMATE;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_TAG_CLASSMATE;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_TAG_TEAMMATE;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.nova.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.nova.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.nova.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.abcommands.AbEditCommand;
import seedu.nova.logic.commands.abcommands.AbEditCommand.EditPersonDescriptor;
import seedu.nova.logic.parser.abparsers.AbEditCommandParser;
import seedu.nova.model.category.Category;
import seedu.nova.model.person.Email;
import seedu.nova.model.person.Name;
import seedu.nova.model.person.Phone;
import seedu.nova.testutil.EditPersonDescriptorBuilder;

public class AbEditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_CATEGORY;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbEditCommand.MESSAGE_USAGE);

    private AbEditCommandParser parser = new AbEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " i\\1", AbEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " i\\1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " i\\1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, " i\\1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, " i\\1" + INVALID_TAG_DESC, Category.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, " i\\1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, " i\\1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, " i\\1" + " c\\classmates", Category.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " i\\1" + " c\\teammates", Category.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " i\\1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = " i\\" + targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_TEAMMATE
                + EMAIL_DESC_AMY + NAME_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withTags(VALID_TAG_TEAMMATE).build();
        AbEditCommand expectedCommand = new AbEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = " i\\" + targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        AbEditCommand expectedCommand = new AbEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = " i\\" + targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        AbEditCommand expectedCommand = new AbEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = " i\\" + targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new AbEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = " i\\" + targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new AbEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = " i\\" + targetIndex.getOneBased() + TAG_DESC_CLASSMATE;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_CLASSMATE).build();
        expectedCommand = new AbEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    /*@Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        AbEditCommand expectedCommand = new AbEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }*/

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = " i\\" + targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        AbEditCommand expectedCommand = new AbEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = " i\\" + targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC
                + PHONE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .build();
        expectedCommand = new AbEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
