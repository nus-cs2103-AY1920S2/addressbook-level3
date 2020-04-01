package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.GoToCommand;

public class GoToCommandParserTest {

    private GoToCommandParser parser = new GoToCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        //Invalid YearMonth
        CommandParserTestUtil.assertParseFailure(parser, " 22-2020",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE));

        //Invalid format
        CommandParserTestUtil.assertParseFailure(parser, " 222-222",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoToCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsGoToCommand() {
        // no leading and trailing whitespaces
        GoToCommand expectedGoToCommand = new GoToCommand("8-2020");
        CommandParserTestUtil.assertParseSuccess(parser, " 8-2020", expectedGoToCommand);

        // leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, " \n 8-2020 \n", expectedGoToCommand);
    }
}
