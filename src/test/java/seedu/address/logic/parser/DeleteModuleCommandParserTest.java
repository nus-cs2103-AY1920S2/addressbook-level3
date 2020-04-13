package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.modulecommand.DeleteModuleCommand;
import seedu.address.model.nusmodule.ModuleCode;

class DeleteModuleCommandParserTest {

    private DeleteModuleCommandParser parser = new DeleteModuleCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, " cs2103", new DeleteModuleCommand(new ModuleCode("CS2103")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteModuleCommand.MESSAGE_USAGE));
    }

}
