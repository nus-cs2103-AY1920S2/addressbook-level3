package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_LIST;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_LIST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DoneCommand;

public class DoneCommandParserTest {

    private DoneCommandParser parser = new DoneCommandParser();

    @Test
    public void parse_validArgs_returnsDoneCommand() {
        DoneCommand.DoneOrderDescriptor descriptor = new DoneCommand.DoneOrderDescriptor();
        assertParseSuccess(parser, "1 -o", new DoneCommand(INDEX_FIRST_ORDER, FLAG_ORDER_LIST, descriptor));
        assertParseSuccess(parser, "2 -o", new DoneCommand(INDEX_SECOND_ORDER, FLAG_ORDER_LIST, descriptor));
        assertParseSuccess(parser, "3 -o", new DoneCommand(INDEX_THIRD_ORDER, FLAG_ORDER_LIST, descriptor));

        assertParseSuccess(parser, "1 -r", new DoneCommand(INDEX_FIRST_ORDER, FLAG_RETURN_LIST, descriptor));
        assertParseSuccess(parser, "2 -r", new DoneCommand(INDEX_SECOND_ORDER, FLAG_RETURN_LIST, descriptor));
        assertParseSuccess(parser, "3 -r", new DoneCommand(INDEX_THIRD_ORDER, FLAG_RETURN_LIST, descriptor));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "@", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }
}
