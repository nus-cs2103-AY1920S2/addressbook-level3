package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.ListCommand;


public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        //Invalid prefix (only a/, u/ or no prefix allowed)
        CommandParserTestUtil.assertParseFailure(parser, " e/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));

        //Multiple valid prefixes
        CommandParserTestUtil.assertParseFailure(parser, " a/ u/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_emptyArg_returnActiveListCommand() {
        ListCommand expectedListCommand = new ListCommand();
        CommandParserTestUtil.assertParseSuccess(parser, "     ", expectedListCommand);
    }

    @Test
    public void parse_validArgs_returnsArchivedListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand = new ListCommand(new Prefix("a/"));
        CommandParserTestUtil.assertParseSuccess(parser, " a/", expectedListCommand);

        // leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, " \n a/ \n", expectedListCommand);
    }

    @Test
    public void parse_validArgs_returnsUsedListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand = new ListCommand(CliSyntax.PREFIX_USAGE);
        CommandParserTestUtil.assertParseSuccess(parser, " u/", expectedListCommand);

        // leading and trailing whitespaces
        CommandParserTestUtil.assertParseSuccess(parser, "\n u/ \n", expectedListCommand);
    }
}
