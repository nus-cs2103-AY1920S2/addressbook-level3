package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_MONTH_YEAR;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.util.DateUtil;
import csdev.couponstash.logic.commands.GoToCommand;

public class GoToCommandParserTest {

    private GoToCommandParser parser = new GoToCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        //Invalid YearMonth
        CommandParserTestUtil.assertParseFailure(parser, " " + PREFIX_MONTH_YEAR + "22-2020",
                DateUtil.MESSAGE_YEAR_MONTH_WRONG_FORMAT);

        //Invalid format
        CommandParserTestUtil.assertParseFailure(parser, " " + PREFIX_MONTH_YEAR + "222-222",
                DateUtil.MESSAGE_YEAR_MONTH_WRONG_FORMAT);

        //Invalid prefix
        CommandParserTestUtil.assertParseFailure(parser, " " + PREFIX_EXPIRY_DATE + "22-2020",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE));

        //No prefix
        CommandParserTestUtil.assertParseFailure(parser, " 12-2020",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsGoToCommand() {
        // no leading and trailing whitespaces
        GoToCommand expectedGoToCommand = new GoToCommand("8-2020");
        CommandParserTestUtil.assertParseSuccess(parser, " " + PREFIX_MONTH_YEAR + "8-2020", expectedGoToCommand);

        // leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, "\n " + PREFIX_MONTH_YEAR + "8-2020 \n", expectedGoToCommand);

        // multiple whitespaces between keywords (Date)
        CommandParserTestUtil.assertParseSuccess(parser, "\n " + PREFIX_MONTH_YEAR + " \n 8-2020 \n",
                expectedGoToCommand);
    }
}
