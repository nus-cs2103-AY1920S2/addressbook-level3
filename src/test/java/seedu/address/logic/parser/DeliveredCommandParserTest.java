package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeliveredCommand;

public class DeliveredCommandParserTest {

    private DeliveredCommandParser parser = new DeliveredCommandParser();

    @Test
    public void parse_validArgs_returnsDoneCommand() {
        DeliveredCommand.DeliveredParcelDescriptor descriptor = new DeliveredCommand.DeliveredParcelDescriptor();
        assertParseSuccess(parser, "1 -o", new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK,
                descriptor));
        assertParseSuccess(parser, "2 -o", new DeliveredCommand(INDEX_SECOND_ORDER, FLAG_ORDER_BOOK,
                descriptor));
        assertParseSuccess(parser, "3 -o", new DeliveredCommand(INDEX_THIRD_ORDER, FLAG_ORDER_BOOK,
                descriptor));

        assertParseSuccess(parser, "-o 1", new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK,
                descriptor));
        assertParseSuccess(parser, "-o 2", new DeliveredCommand(INDEX_SECOND_ORDER, FLAG_ORDER_BOOK,
                descriptor));
        assertParseSuccess(parser, "-o 3", new DeliveredCommand(INDEX_THIRD_ORDER, FLAG_ORDER_BOOK,
                descriptor));

        assertParseSuccess(parser, "1 -r", new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK,
                descriptor));
        assertParseSuccess(parser, "2 -r", new DeliveredCommand(INDEX_SECOND_ORDER, FLAG_RETURN_BOOK,
                descriptor));
        assertParseSuccess(parser, "3 -r", new DeliveredCommand(INDEX_THIRD_ORDER, FLAG_RETURN_BOOK,
                descriptor));

        assertParseSuccess(parser, "-r 1", new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK,
                descriptor));
        assertParseSuccess(parser, "-r 2", new DeliveredCommand(INDEX_SECOND_ORDER, FLAG_RETURN_BOOK,
                descriptor));
        assertParseSuccess(parser, "-r 3", new DeliveredCommand(INDEX_THIRD_ORDER, FLAG_RETURN_BOOK,
                descriptor));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeliveredCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "@", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeliveredCommand.MESSAGE_USAGE));
    }
}
