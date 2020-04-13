package seedu.address.logic.parser.module;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.module.ModuleViewCommand;
import seedu.address.model.module.ModuleCode;

public class ModuleViewCommandParserTest {
    private ModuleViewCommandParser parser = new ModuleViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ModuleViewCommand expectedViewCommand = new ModuleViewCommand(new ModuleCode(VALID_MODULE_CODE_CS2101));
        assertParseSuccess(parser, "CS2101", expectedViewCommand);

        // leading and trailing whitespaces
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "CS2101" + PREAMBLE_WHITESPACE, expectedViewCommand);

        // no leading and trailing whitespaces
        expectedViewCommand = new ModuleViewCommand(INDEX_FIRST);
        assertParseSuccess(parser, "1", expectedViewCommand);

        // leading and trailing whitespaces
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + "1" + PREAMBLE_WHITESPACE, expectedViewCommand);

    }

    @Test
    public void parse_invalidArgs_failure() {
        // invalid module code
        assertParseFailure(parser, " cs2103*",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleViewCommand.MESSAGE_USAGE));

        // multiple module codes
        assertParseFailure(parser, "CS2101 CS2103T",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleViewCommand.MESSAGE_USAGE));

        // invalid index
        assertParseFailure(parser, "-123", Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);

        // multiple index
        assertParseFailure(parser, "1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleViewCommand.MESSAGE_USAGE));
    }
}
