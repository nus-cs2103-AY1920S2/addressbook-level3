package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.NavigationQuestionCommand;
import seedu.address.logic.commands.UploadResumeCommand;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtility.VALID_INTERVIEWEE_JANE;
import static seedu.address.logic.commands.CommandTestUtility.VALID_PATH_DOWNLOADS;
import static seedu.address.logic.commands.CommandTestUtility.VALID_QUESTION_NUMBER_14;
import static seedu.address.logic.commands.CommandTestUtility.WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

class UploadResumeCommandParserTest {

    private UploadResumeCommandParser parser = new UploadResumeCommandParser();

    @Test
    void parse_noPathField_success() {
        assertParseSuccess(parser, WHITESPACE + VALID_INTERVIEWEE_JANE,
                new UploadResumeCommand("Jane Doe", null));
    }

    @Test
    void parse_withPathField_success() {
        assertParseSuccess(parser, WHITESPACE + VALID_INTERVIEWEE_JANE + WHITESPACE
                        + PREFIX_PATH + WHITESPACE + VALID_PATH_DOWNLOADS,
                new UploadResumeCommand("Jane Doe", "/Downloads"));
    }

    @Test
    public void parse_argumentMissing_failure() {
        assertParseFailure(parser, WHITESPACE + WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadResumeCommand.MESSAGE_USAGE));
    }
}