package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static nasa.logic.commands.CommandTestUtil.MODULE_CODE_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1231;
import static nasa.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.module.DeleteModuleCommand;
import nasa.logic.parser.module.DeleteModuleCommandParser;
import nasa.model.module.ModuleCode;

//@@author kester-ng
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteModuleCommandParserTest {

    private DeleteModuleCommandParser parser = new DeleteModuleCommandParser();
    private ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1231); // dummy module

    @Test
    public void parse_validArgs_returnsDeleteModuleCommand() {
        assertParseSuccess(parser, MODULE_CODE_DESC_CS1231, new DeleteModuleCommand(moduleCode));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no PREFIX_MODULE
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteModuleCommand.MESSAGE_USAGE));

        // invalid argument
        assertParseFailure(parser, INVALID_MODULE_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteModuleCommand.MESSAGE_USAGE));
    }
}
