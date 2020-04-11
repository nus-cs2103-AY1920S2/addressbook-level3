package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowBirthdayCommand;

public class ShowBirthdayCommandParserTest {
    private ShowBirthdayCommandParser parser = new ShowBirthdayCommandParser();

    @Test
    public void parse_argumentsPresent_failure() {
        // additional arguments
        String userInput = " " + PREFIX_NAME;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowBirthdayCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_noArguments_success() {
        ShowBirthdayCommand expectedCommand = new ShowBirthdayCommand();
        assertParseSuccess(parser, "", expectedCommand);
    }
}
