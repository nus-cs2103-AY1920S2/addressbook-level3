package seedu.nova.logic.parser;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.nova.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.nova.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.nova.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.nova.logic.commands.CommandTestUtil.TAG_DESC_CLASSMATE;
import static seedu.nova.logic.commands.CommandTestUtil.TAG_DESC_TEAMMATE;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_TAG_CLASSMATE;
import static seedu.nova.logic.commands.CommandTestUtil.VALID_TAG_TEAMMATE;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.nova.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.nova.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.abcommands.AbAddCommand;
import seedu.nova.logic.parser.abparsers.AbAddCommandParser;
//import seedu.nova.model.person.Address;
import seedu.nova.model.category.Category;
import seedu.nova.model.person.Email;
import seedu.nova.model.person.Name;
import seedu.nova.model.person.Person;
import seedu.nova.model.person.Phone;
import seedu.nova.testutil.PersonBuilder;

public class AbAddCommandParserTest {
    private AbAddCommandParser parser = new AbAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_CLASSMATE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_CLASSMATE, new AbAddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_CLASSMATE, new AbAddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_CLASSMATE, new AbAddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + TAG_DESC_CLASSMATE, new AbAddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_CLASSMATE, new AbAddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_TEAMMATE)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_TEAMMATE, new AbAddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_CLASSMATE,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + TAG_DESC_CLASSMATE,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + TAG_DESC_CLASSMATE,
                expectedMessage);

        // missing category prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_TAG_CLASSMATE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_TAG_CLASSMATE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_TEAMMATE + TAG_DESC_CLASSMATE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + TAG_DESC_TEAMMATE + TAG_DESC_CLASSMATE, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + TAG_DESC_TEAMMATE + TAG_DESC_CLASSMATE, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_CLASSMATE, Category.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_TEAMMATE + TAG_DESC_CLASSMATE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbAddCommand.MESSAGE_USAGE));
    }
}
