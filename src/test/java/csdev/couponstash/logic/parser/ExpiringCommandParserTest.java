package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.commons.util.DateUtil.YEAR_MONTH_FORMATTER;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.ExpiringCommand;
import csdev.couponstash.model.coupon.DateIsEqualsPredicate;
import csdev.couponstash.model.coupon.DateIsInMonthYearPredicate;

public class ExpiringCommandParserTest {

    private ExpiringCommandParser parser = new ExpiringCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        String invalidInput = "22-2020";
        CommandParserTestUtil.assertParseFailure(parser, invalidInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE));

        invalidInput = "";
        CommandParserTestUtil.assertParseFailure(parser, invalidInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE));

        invalidInput = "222-22-2020";
        CommandParserTestUtil.assertParseFailure(parser, invalidInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsExpiringCommand() {

        // no leading and trailing whitespaces (MonthYear)
        ExpiringCommand expectedExpiringCommand =
                new ExpiringCommand(new DateIsInMonthYearPredicate(YearMonth.parse("8-2020", YEAR_MONTH_FORMATTER)));
        CommandParserTestUtil.assertParseSuccess(parser, "8-2020", expectedExpiringCommand);

        // multiple whitespaces between keywords (MonthYear)
        CommandParserTestUtil.assertParseSuccess(parser, " \n 8-2020 \n", expectedExpiringCommand);

        // no leading and trailing whitespaces (Date)
        expectedExpiringCommand =
                new ExpiringCommand(new DateIsEqualsPredicate("30-8-2020"));
        CommandParserTestUtil.assertParseSuccess(parser, "30-8-2020", expectedExpiringCommand);

        // multiple whitespaces between keywords (Date)
        CommandParserTestUtil.assertParseSuccess(parser, " \n 30-8-2020 \n", expectedExpiringCommand);
    }
}
