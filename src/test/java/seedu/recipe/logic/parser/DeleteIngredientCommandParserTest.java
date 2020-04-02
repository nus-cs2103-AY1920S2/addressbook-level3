package seedu.recipe.logic.parser;

import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;

import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.DeleteIngredientCommand;
import seedu.recipe.logic.commands.EditCommand;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteIngredientCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteIngredientCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteIngredientCommandParserTest {

    private DeleteIngredientCommandParser parser = new DeleteIngredientCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteIngredientCommand() {
        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        editRecipeDescriptor.setOthers(new TreeSet<>());
        assertParseSuccess(parser, "2 io/",
                new DeleteIngredientCommand(INDEX_SECOND_RECIPE, editRecipeDescriptor));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a io/", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_insufficientArgs_throwsParseException() {
        assertParseFailure(parser, "2", EditCommand.MESSAGE_NOT_EDITED);
    }
}
