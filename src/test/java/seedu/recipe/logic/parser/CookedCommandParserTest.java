package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;

import org.junit.jupiter.api.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.CookedCommand;

class CookedCommandParserTest {

    private CookedCommandParser parser = new CookedCommandParser();

    @Test
    public void parse_validArgs_returnsCookedCommand() {
        assertParseSuccess(parser, "1", new CookedCommand(new Index[] {INDEX_FIRST_RECIPE}));
    }

    @Test
    public void parse_multipleValidArgs_returnsCookedCommand() {
        assertParseSuccess(parser, "1 2", new CookedCommand(new Index[] {INDEX_FIRST_RECIPE, INDEX_SECOND_RECIPE}));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CookedCommand.MESSAGE_USAGE));
    }
}
