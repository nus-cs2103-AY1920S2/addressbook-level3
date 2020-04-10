package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.profile.Name;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // Name present
        Name name = new Name(VALID_NAME_BOB);
        assertParseSuccess(parser, " " + PREFIX_NAME + VALID_NAME_BOB, new DeleteCommand(name));

        // Module field present
        //ModuleCode moduleCode = new ModuleCode("CS1101S");
        //assertParseSuccess(parser, " " + PREFIX_MODULE + moduleCode, new ShowCommand(moduleCode));

        //Focus area field present
        //String focusArea = "Computer Security";
        //assertParseSuccess(parser, " " + PREFIX_FOCUS_AREA + focusArea,
        //        new ShowCommand(focusArea.toUpperCase()));

        // Name field present
        //String name = "john";
        //assertParseSuccess(parser, " " + PREFIX_NAME + name, new ShowCommand(new Name(name)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
