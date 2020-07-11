package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_MONTH_YEAR;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.logic.commands.ExpiringCommand;
import csdev.couponstash.model.coupon.DateIsEqualsPredicate;
import csdev.couponstash.model.coupon.DateIsInMonthYearPredicate;

public class ExpiringCommandParserTest {

    private ExpiringCommandParser parser = new ExpiringCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        //Expiry Date prefix with YearMonth parameter
        CommandParserTestUtil.assertParseFailure(parser, PREFIX_EXPIRY_DATE + "12-2020",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE));

        //Invalid expiry date
        CommandParserTestUtil.assertParseFailure(parser, PREFIX_EXPIRY_DATE + "31-2-2020",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE));

        //YearMonth prefix with Expiry Date parameter
        CommandParserTestUtil.assertParseFailure(parser, PREFIX_MONTH_YEAR + "31-12-2020",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE));

        //Invalid YearMonth
        CommandParserTestUtil.assertParseFailure(parser, PREFIX_MONTH_YEAR + "13-2020",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE));

        //Invalid prefix
        CommandParserTestUtil.assertParseFailure(parser, PREFIX_NAME + "31-12-2020",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE));

        //No prefix
        CommandParserTestUtil.assertParseFailure(parser, " 31-12-2020",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpiringCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsExpiringCommand() {

        // no leading and trailing whitespaces (MonthYear)
        ExpiringCommand expectedExpiringCommand =
                new ExpiringCommand(new DateIsInMonthYearPredicate(DateUtil.parseStringToYearMonth("8-2020")));
        CommandParserTestUtil.assertParseSuccess(parser, "\n " + PREFIX_MONTH_YEAR + "8-2020",
                expectedExpiringCommand);

        // multiple whitespaces between keywords (MonthYear)
        CommandParserTestUtil.assertParseSuccess(parser, "\n " + PREFIX_MONTH_YEAR + "8-2020 \n",
                expectedExpiringCommand);

        // no leading and trailing whitespaces (Date)
        expectedExpiringCommand =
                new ExpiringCommand(new DateIsEqualsPredicate("30-08-2020"));
        CommandParserTestUtil.assertParseSuccess(parser, "\n " + PREFIX_EXPIRY_DATE + "30-8-2020",
                expectedExpiringCommand);

        // multiple whitespaces between keywords (Date)
        CommandParserTestUtil.assertParseSuccess(parser, "\n " + PREFIX_EXPIRY_DATE + " \n 30-8-2020 \n",
                expectedExpiringCommand);
    }
}
