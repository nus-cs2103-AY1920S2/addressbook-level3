package seedu.jelphabot.logic.parser;

import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jelphabot.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.logic.commands.FindCommand;
import seedu.jelphabot.model.task.predicates.DescriptionContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
            parser,
            "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE)
        );
    }

    @Test
    public void parse_predicate_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
            new FindCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

}
