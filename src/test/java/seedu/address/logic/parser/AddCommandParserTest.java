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

        assertParseSuccess(parser, CommandTestUtility.PREAMBLE_WHITESPACE
                + CommandTestUtility.VALID_PROPERTY_INTERVIEWEE
                + CommandTestUtility.PREAMBLE_WHITESPACE
                + CommandTestUtility.VALID_INTERVIEWEE_JANE,
                new AddIntervieweeCommand("Jane Doe"));

        assertParseSuccess(parser, CommandTestUtility.PREAMBLE_WHITESPACE
                        + CommandTestUtility.VALID_PROPERTY_ATTRIBUTE
                        + CommandTestUtility.PREAMBLE_WHITESPACE
                        + CommandTestUtility.VALID_ATTRIBUTE_PERSISTENCE,
                new AddAttributeCommand("persistence"));

        assertParseSuccess(parser, CommandTestUtility.PREAMBLE_WHITESPACE
                        + CommandTestUtility.VALID_PROPERTY_QUESTION
                        + CommandTestUtility.PREAMBLE_WHITESPACE
                        + CommandTestUtility.VALID_QUESTION_WHAT,
                new AddQuestionCommand("What is this question?"));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, CommandTestUtility.PREAMBLE_WHITESPACE, expectedMessage);
    }
}
