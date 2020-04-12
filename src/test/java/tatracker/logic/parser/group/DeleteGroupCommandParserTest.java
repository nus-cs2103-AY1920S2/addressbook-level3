//@@author aakanksha-rai

package tatracker.logic.parser.group;

import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.group.DeleteGroupCommand;


public class DeleteGroupCommandParserTest {

    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedModule = "CS3243";
        String expectedGroup = "G03";

        // whitespace only preamble
        assertParseSuccess(parser, " m/CS3243 g/G03", new DeleteGroupCommand(expectedGroup, expectedModule));

        // different order
        assertParseSuccess(parser, " g/G03 m/CS3243", new DeleteGroupCommand(expectedGroup, expectedModule));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = Messages.getInvalidCommandMessage(DeleteGroupCommand.DETAILS.getUsage());

        // missing group prefix
        assertParseFailure(parser, " m/CS3243",
                expectedMessage);

        // missing type prefix
        assertParseFailure(parser, " g/G03",
                expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, " ",
                expectedMessage);
    }
}
