package seedu.address.logic.parser.module;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.module.ModuleDeleteCommand;
import seedu.address.model.module.ModuleCode;

public class ModuleDeleteCommandParserTest {
    private ModuleDeleteCommandParser parser = new ModuleDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new ModuleDeleteCommand(INDEX_FIRST));
        assertParseSuccess(parser, "CS2030", new ModuleDeleteCommand(new ModuleCode("CS2030")));
        assertParseSuccess(parser, "CS2040S", new ModuleDeleteCommand(new ModuleCode("CS2040S")));
        assertParseSuccess(parser, "PCS2030", new ModuleDeleteCommand(new ModuleCode("PCS2030")));
        assertParseSuccess(parser, "PCS2030T", new ModuleDeleteCommand(new ModuleCode("PCS2030T")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-123", Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        assertParseFailure(parser, "CS 324",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleDeleteCommand.MESSAGE_USAGE));
    }
}
