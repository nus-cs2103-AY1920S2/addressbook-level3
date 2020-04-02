package seedu.recipe.logic.parser;

import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.EditCommand;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.logic.commands.EditIngredientCommand;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.testutil.RecipeBuilder;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the EditIngredientCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the EditIngredientCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class EditIngredientCommandParserTest {

    private EditIngredientCommandParser parser = new EditIngredientCommandParser();
    private final Other otherWithNewQuantity = new Other("Cheese", RecipeBuilder.DEFAULT_QUANTITY);

    @Test
    public void parse_validArgs_returnsEditIngredientCommand() {
        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();
        Set<Other> editedOther = new TreeSet<>();
        editedOther.add(otherWithNewQuantity);
        editRecipeDescriptor.setOthers(editedOther);

        assertParseSuccess(parser, "2 io/100g, Cheese",
                new EditIngredientCommand(INDEX_SECOND_RECIPE, editRecipeDescriptor));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "a io/100g, Cheese", ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_insufficientArgs_throwsParseException() {
        assertParseFailure(parser, "2", EditCommand.MESSAGE_NOT_EDITED);
    }
}
