package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtility.INVALID_DUMMY_VALUE;
import static seedu.address.logic.commands.CommandTestUtility.WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FinaliseCommand;

class FinaliseCommandParserTest {

    private FinaliseCommandParser parser = new FinaliseCommandParser();

    @Test
    void parse_noFieldsPresent_success() {
        assertParseSuccess(parser, "",
                new FinaliseCommand());
    }

    @Test
    void parse_fieldsPresent_failure() {
        assertParseFailure(parser, WHITESPACE + INVALID_DUMMY_VALUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinaliseCommand.MESSAGE_USAGE));
    }
}
