//@@author aakanksha-rai

package tatracker.logic.parser.module;

import static tatracker.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tatracker.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.module.AddModuleCommand;
import tatracker.model.module.Module;
import tatracker.testutil.module.ModuleBuilder;

public class AddModuleCommandParserTest {

    private AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Module expectedModule = new ModuleBuilder().withIdentifier("CS3243")
                .withName("Intro to AI").build();

        // whitespace only preamble
        assertParseSuccess(parser, " m/CS3243 n/Intro To AI", new AddModuleCommand(expectedModule));

        // different order
        assertParseSuccess(parser, " n/Intro To AI m/CS3243", new AddModuleCommand(expectedModule));

        // multiple names - last name accepted
        // different order
        assertParseSuccess(parser, " n/Fake Name n/Intro To AI m/CS3243", new AddModuleCommand(expectedModule));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = Messages.getInvalidCommandMessage(AddModuleCommand.DETAILS.getUsage());

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
