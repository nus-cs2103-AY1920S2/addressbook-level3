package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.*;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();


    @Test
    public void parse_allFieldsPresent_success() {

        assertParseSuccess(parser, " interviewee Jone Doe",
                new AddIntervieweeCommand("Jone Doe"));

        assertParseSuccess(parser, " attribute persistence",
                new AddAttributeCommand("persistence"));

        assertParseSuccess(parser, " question What is your age",
                new AddQuestionCommand("What is your age"));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " ", expectedMessage);
    }
}
