package seedu.delino.logic.parser;

import static seedu.delino.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.delino.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.delino.commons.core.Messages;
import seedu.delino.logic.commands.HelpCommand;

public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "asdasdasd", String.format(HelpCommand.SHOW_ADDITIONAL_PARAMETERS_MESSAGE
                + Messages.NEWLINE + HelpCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1234", String.format(HelpCommand.SHOW_ADDITIONAL_PARAMETERS_MESSAGE
                + Messages.NEWLINE + HelpCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " abc123", String.format(HelpCommand.SHOW_ADDITIONAL_PARAMETERS_MESSAGE
                + Messages.NEWLINE + HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsHelpCommand() {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.setValidity(true);
        assertParseSuccess(parser, "", helpCommand);
    }
}
