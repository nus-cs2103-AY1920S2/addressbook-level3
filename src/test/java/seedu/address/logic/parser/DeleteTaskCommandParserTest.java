package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.taskcommand.deletecommand.DeleteModuleTaskCommand;
import seedu.address.logic.commands.taskcommand.deletecommand.DeleteTaskCommand;
import seedu.address.model.nusmodule.ModuleCode;

class DeleteTaskCommandParserTest {
    private DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ModuleCode expectedModuleCode = new ModuleCode("CS2030");

        assertParseSuccess(parser, " m/CS2030 index/1",
                new DeleteModuleTaskCommand(expectedModuleCode, Index.fromOneBased(1)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " CS2103", expectedMessage);
        assertParseFailure(parser, " m/CS2103", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid module code
        assertParseFailure(parser, " m/asdasd index/1", ModuleCode.MESSAGE_CONSTRAINTS);
    }
}
