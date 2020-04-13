package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_SPACES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIND_WORDS_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIND_WORDS_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIND_WORDS_PROGRAMMING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.TaskFindCommand;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

public class TaskFindCommandParserTest {

    private TaskFindCommandParser parser = new TaskFindCommandParser();

    @Test
    public void parse_emptyArguments_failure() {
        assertParseFailure(parser, EMPTY_SPACES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArguments_success() {
        // no leading and trailing whitespaces
        TaskFindCommand expectedFindCommand = new TaskFindCommand(
                new TaskContainsKeywordsPredicate(Arrays.asList(
                        VALID_FIND_WORDS_PROGRAMMING,
                        VALID_FIND_WORDS_ASSIGNMENT,
                        VALID_FIND_WORDS_HOMEWORK)));
        assertParseSuccess(parser,
                VALID_FIND_WORDS_PROGRAMMING
                + " " + VALID_FIND_WORDS_ASSIGNMENT
                + " " + VALID_FIND_WORDS_HOMEWORK, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n "
                + VALID_FIND_WORDS_PROGRAMMING
                + "\n \t  "
                + VALID_FIND_WORDS_ASSIGNMENT
                + "  \r    \r"
                + VALID_FIND_WORDS_HOMEWORK
                + "      \t", expectedFindCommand);
    }

}
