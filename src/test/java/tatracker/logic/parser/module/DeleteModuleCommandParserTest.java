//@@author aakanksha-rai

package tatracker.logic.parser.module;

import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.module.DeleteModuleCommand;

public class DeleteModuleCommandParserTest {

    private DeleteModuleCommandParser parser = new DeleteModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedModule = "CS3243";

        // whitespace only preamble
        assertParseSuccess(parser, " m/CS3243", new DeleteModuleCommand(expectedModule));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = Messages.getInvalidCommandMessage(DeleteModuleCommand.DETAILS.getUsage());

        // missing module code prefix
        assertParseFailure(parser, " ",
                expectedMessage);
    }
}
