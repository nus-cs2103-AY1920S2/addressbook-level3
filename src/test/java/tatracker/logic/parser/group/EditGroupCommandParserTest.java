//@@author aakanksha-rai

package tatracker.logic.parser.group;

import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.group.EditGroupCommand;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;

public class EditGroupCommandParserTest {

    private EditGroupCommandParser parser = new EditGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedModule = "CS3243";
        Group expectedGroup = new Group("G03");
        String newGroup = "G04";
        GroupType newType = GroupType.LAB;


        // whitespace only preamble
        assertParseSuccess(parser, " m/CS3243 g/G03 ng/G04 nt/lab",
                new EditGroupCommand(expectedGroup, expectedModule, newGroup, newType));

        // different order
        assertParseSuccess(parser, " m/CS3243 ng/G04 g/G03 nt/lab",
                new EditGroupCommand(expectedGroup, expectedModule, newGroup, newType));

        assertParseSuccess(parser, " m/CS3243 g/G03 nt/lab",
                new EditGroupCommand(expectedGroup, expectedModule, expectedGroup.getIdentifier(), newType));

        assertParseSuccess(parser, " m/CS3243 ng/G04 g/G03",
                new EditGroupCommand(expectedGroup, expectedModule, newGroup, null));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = Messages.getInvalidCommandMessage(EditGroupCommand.DETAILS.getUsage());

        // missing group prefix
        assertParseFailure(parser, " m/CS3243 ng/G04 nt/lab",
                expectedMessage);

        // missing module prefix
        assertParseFailure(parser, " g/G03 ng/G04 nt/lab",
                expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, " ",
                expectedMessage);
    }
}
