package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_MONEY_SYMBOL;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.UsedCommand;
import csdev.couponstash.testutil.TypicalIndexes;

public class UsedCommandParserTest {
    private UsedCommandParser parser = new UsedCommandParser(VALID_MONEY_SYMBOL);

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser,
                "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UsedCommand.MESSAGE_USAGE));
    }

    @Test void parse_validArgs_returnsUsedCommand() {
        UsedCommand expectedFirstUsedCommand = new UsedCommand(TypicalIndexes.INDEX_FIRST_COUPON);
        CommandParserTestUtil.assertParseSuccess(parser, "1", expectedFirstUsedCommand);

        UsedCommand expectedSecondUsedCommand = new UsedCommand(TypicalIndexes.INDEX_SECOND_COUPON);
        CommandParserTestUtil.assertParseSuccess(parser, "2 s/" + VALID_MONEY_SYMBOL + "10",
                expectedSecondUsedCommand);
    }
}
