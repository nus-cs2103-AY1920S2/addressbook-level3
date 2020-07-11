package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_MONEY_SYMBOL;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.UsedCommand;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;
import csdev.couponstash.testutil.TypicalIndexes;

public class UsedCommandParserTest {
    private UsedCommandParser parser = new UsedCommandParser(VALID_MONEY_SYMBOL.toString());

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String index = "a";
        CommandParserTestUtil.assertParseFailure(parser,
                index, String.format(ParserUtil.MESSAGE_INVALID_INDEX, index)
                        + "\n\n" + String.format(
                                MESSAGE_INVALID_COMMAND_FORMAT,
                                String.format(
                                        UsedCommand.MESSAGE_USAGE,
                                        VALID_MONEY_SYMBOL,
                                        VALID_MONEY_SYMBOL
                                )
                )
        );
    }

    @Test void parse_validArgs_returnsUsedCommand() {
        UsedCommand expectedFirstUsedCommand = new UsedCommand(TypicalIndexes.INDEX_FIRST_COUPON);
        CommandParserTestUtil.assertParseSuccess(parser, "1", expectedFirstUsedCommand);

        MonetaryAmount amount = new MonetaryAmount(10, 0);
        UsedCommand expectedSecondUsedCommand = new UsedCommand(TypicalIndexes.INDEX_SECOND_COUPON, amount);
        CommandParserTestUtil.assertParseSuccess(parser, "2 " + VALID_MONEY_SYMBOL + "10",
                expectedSecondUsedCommand);
    }

    @Test
    public void parse_integerOverflow_throwsParseException() {
        String message = ParserUtil.MESSAGE_INDEX_OVERFLOW + "\n\n"
                + String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT,
                        String.format(
                                UsedCommand.MESSAGE_USAGE,
                                VALID_MONEY_SYMBOL,
                                VALID_MONEY_SYMBOL
                        )
        );

        String exceedMaxValue = Long.toString(Integer.MAX_VALUE + 1L);

        CommandParserTestUtil.assertParseFailure(parser, exceedMaxValue, message);
    }
}
