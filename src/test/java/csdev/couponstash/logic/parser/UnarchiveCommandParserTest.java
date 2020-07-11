package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.UnarchiveCommand;
import csdev.couponstash.testutil.TypicalIndexes;

public class UnarchiveCommandParserTest {
    private UnarchiveCommandParser parser = new UnarchiveCommandParser();

    @Test
    public void parse_validArgs_returnsUnarchiveCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1",
                new UnarchiveCommand(TypicalIndexes.INDEX_FIRST_COUPON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String index = "a";
        CommandParserTestUtil.assertParseFailure(parser,
                index, String.format(ParserUtil.MESSAGE_INVALID_INDEX, index) + "\n\n"
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_integerOverflow_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser,
                Long.toString(Integer.MAX_VALUE + 1L),
                ParserUtil.MESSAGE_INDEX_OVERFLOW + "\n\n"
                        + String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE)
        );
    }
}
