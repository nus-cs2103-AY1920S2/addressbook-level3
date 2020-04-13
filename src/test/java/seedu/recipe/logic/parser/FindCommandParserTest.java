package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.recipe.FindCommand;
import seedu.recipe.model.recipe.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsNonStrictFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(false, "Chicken rice"));
        assertParseSuccess(parser, "Chicken rice", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Chicken \n \t rice  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsStrictFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(true, "Chicken rice"));
        assertParseSuccess(parser, "/strict Chicken rice", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " /strict \n Chicken \n \t rice  \t", expectedFindCommand);
    }

}
