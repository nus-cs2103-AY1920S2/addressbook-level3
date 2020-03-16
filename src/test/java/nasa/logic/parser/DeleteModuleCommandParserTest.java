package nasa.logic.parser;

import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nasa.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nasa.logic.commands.CommandTestUtil.MODULE_DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static nasa.logic.commands.CommandTestUtil.VALID_MODULE_CS1231;

import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import org.junit.jupiter.api.Test;

import nasa.logic.commands.DeleteModuleCommand;

public class DeleteModuleCommandParserTest {

    private DeleteModuleCommandParser parser = new DeleteModuleCommandParser();
    private ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CS1231); // dummy module

    @Test
    public void parse_validArgs_returnsDeleteModuleCommand() {
        assertParseSuccess(parser, MODULE_DESC_CS1231, new DeleteModuleCommand(moduleCode));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no PREFIX_MODULE
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModuleCommand.MESSAGE_USAGE));

        // invalid argument
        assertParseFailure(parser, INVALID_MODULE_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModuleCommand.MESSAGE_USAGE));
    }
}
