package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.HelpCommand;

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
