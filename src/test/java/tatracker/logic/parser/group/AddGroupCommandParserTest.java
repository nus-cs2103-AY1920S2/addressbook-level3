//@@author aakanksha-rai

package tatracker.logic.parser.group;

import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.group.AddGroupCommand;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;

public class AddGroupCommandParserTest {

    private AddGroupCommandParser parser = new AddGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedModule = "CS3243";
        Group expectedGroup = new Group("G03", GroupType.LAB);

        // whitespace only preamble
        assertParseSuccess(parser, " m/CS3243 g/G03 t/lab", new AddGroupCommand(expectedGroup, expectedModule));

        // different order
        assertParseSuccess(parser, " g/G03 m/CS3243 t/lab", new AddGroupCommand(expectedGroup, expectedModule));

        // multiple names - last name accepted
        assertParseSuccess(parser, " m/CS3243 g/G05 t/lab g/G03", new AddGroupCommand(expectedGroup, expectedModule));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = Messages.getInvalidCommandMessage(AddGroupCommand.DETAILS.getUsage());

        // missing group prefix
        assertParseFailure(parser, " m/CS3243 t/lab",
                expectedMessage);

        // missing type prefix
        assertParseFailure(parser, " m/CS3243 g/G03",
                expectedMessage);

        // missing type prefix
        assertParseFailure(parser, " t/lab g/G03",
                expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, " ",
                expectedMessage);
    }
}
