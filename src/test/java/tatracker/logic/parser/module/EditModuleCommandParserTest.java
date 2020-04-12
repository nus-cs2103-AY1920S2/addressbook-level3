//@@author aakanksha-rai

package tatracker.logic.parser.module;

import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.module.EditModuleCommand;

public class EditModuleCommandParserTest {

    private EditModuleCommandParser parser = new EditModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedModule = "CS3243";
        String expectedName = "Intro To AI";

        // whitespace only preamble
        assertParseSuccess(parser, " m/CS3243 n/Intro To AI", new EditModuleCommand(expectedModule, expectedName));

        // different order
        assertParseSuccess(parser, " n/Intro To AI m/CS3243", new EditModuleCommand(expectedModule, expectedName));

        // multiple names - last name accepted
        // different order
        assertParseSuccess(parser, " n/Fake Name n/Intro To AI m/CS3243",
                new EditModuleCommand(expectedModule, expectedName));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = Messages.getInvalidCommandMessage(EditModuleCommand.DETAILS.getUsage());

        // missing name prefix
        assertParseFailure(parser, " m/CS3243",
                expectedMessage);

        // missing module code prefix
        assertParseFailure(parser, " n/Intro to AI",
                expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, " ",
                expectedMessage);
    }
}
