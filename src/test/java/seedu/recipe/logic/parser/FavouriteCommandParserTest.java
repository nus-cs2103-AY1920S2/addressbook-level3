package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.recipe.FavouriteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the FavouriteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the FavouriteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class FavouriteCommandParserTest {

    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_validSingleArg_returnsFavouriteCommand() {
        assertParseSuccess(parser, "1", new FavouriteCommand(new Index[] {INDEX_FIRST_RECIPE}));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FavouriteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleDescendingArgs_returnsFavouriteCommand() {
        assertParseSuccess(parser, "2 1",
                new FavouriteCommand(new Index[] {INDEX_FIRST_RECIPE, INDEX_SECOND_RECIPE}));
    }
}
