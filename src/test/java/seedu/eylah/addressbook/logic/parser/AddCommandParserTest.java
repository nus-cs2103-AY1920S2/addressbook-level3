package seedu.eylah.addressbook.logic.parser;

import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;

import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.eylah.addressbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eylah.addressbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import static seedu.eylah.addressbook.testutil.TypicalPersons.AMY;
import static seedu.eylah.addressbook.testutil.TypicalPersons.BOB;

import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.eylah.addressbook.logic.commands.AddCommand;

import seedu.eylah.addressbook.model.person.Name;
import seedu.eylah.addressbook.model.person.Person;
import seedu.eylah.addressbook.testutil.PersonBuilder;
import seedu.eylah.diettracker.model.tag.Tag;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();

        assertParseSuccess(parser, NAME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();

        assertParseSuccess(parser, NAME_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, VALID_NAME_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
