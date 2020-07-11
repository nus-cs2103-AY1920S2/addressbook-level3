package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.ArchiveCommand;
import csdev.couponstash.testutil.TypicalIndexes;

public class ArchiveCommandParserTest {
    private ArchiveCommandParser parser = new ArchiveCommandParser();

    @Test
    public void parse_validArgs_returnsArchiveCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1",
                new ArchiveCommand(TypicalIndexes.INDEX_FIRST_COUPON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String index = "a";
        CommandParserTestUtil.assertParseFailure(parser,
                index, String.format(
                        String.format(ParserUtil.MESSAGE_INVALID_INDEX, index) + "\n\n"
                                + MESSAGE_INVALID_COMMAND_FORMAT,
                        ArchiveCommand.MESSAGE_USAGE
                ));
    }

    @Test
    public void parse_integerOverflow_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser,
                Long.toString(Integer.MAX_VALUE + 1L),
                String.format(
                        ParserUtil.MESSAGE_INDEX_OVERFLOW + "\n\n"
                                + MESSAGE_INVALID_COMMAND_FORMAT,
                        ArchiveCommand.MESSAGE_USAGE
                )
        );
    }
}
