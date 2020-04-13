package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEMBER_NOT_INT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_SPECIALCHAR;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_RC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SOC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalGroups.RC_NO_TIME;
import static seedu.address.testutil.TypicalGroups.SOC_NO_TIME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;
import seedu.address.testutil.GroupBuilder;

public class AddGroupCommandParserTest {

    private AddGroupCommandParser parser = new AddGroupCommandParser();

    @Test
    public void parse_allFields_success() {
        Group expectedGroup = new GroupBuilder(RC_NO_TIME).build();

        // normal execution
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_RC + MEMBER_DESC_1 + MEMBER_DESC_2,
                new AddGroupCommand(expectedGroup)
        );

        // multiple names - last name accepted
        assertParseSuccess(parser,
                NAME_DESC_SOC + NAME_DESC_RC + MEMBER_DESC_1 + MEMBER_DESC_2,
                new AddGroupCommand(expectedGroup)
        );
    }

    @Test
    public void parse_withoutMembersIds_success() {
        Group expectedGroup = new GroupBuilder(SOC_NO_TIME).build();
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_SOC, new AddGroupCommand(expectedGroup));
    }

    @Test
    public void parse_withInvalidMemberIds_fail() {
        String expectedMessage = AddGroupCommandParser.MEMBER_NOT_INT;
        assertParseFailure(parser, NAME_DESC_RC + INVALID_MEMBER_NOT_INT, expectedMessage);
    }

    @Test
    public void parse_withInvalidName_fail() {
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, INVALID_NAME_SPECIALCHAR, expectedMessage);
    }
}
