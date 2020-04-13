package seedu.address.logic.parser.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cart.CartAddIngredientCommand;
import seedu.address.logic.commands.cart.CartAddRecipeIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

public class CartAddCommandParserTest {
    private static final Ingredient VALID_INGREDIENT = new Ingredient(new IngredientName("Ingredient"),
            new IngredientQuantity("5"));
    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final String VALID_INGREDIENT_ARGUMENT = "ingredient i/Ingredient q/5";
    private static final String INVALID_INGREDIENT_ARGUMENT_NO_NAME = " q/5";
    private static final String INVALID_INGREDIENT_ARGUMENT_NO_QUANTITY = " i/Ingredient";
    private static final String VALID_RECIPE_INDEX_ARGUMENT = "recipe 1";
    private static final String INVALID_RECIPE_INDEX_ARGUMENT_NO_RECIPE = "word";
    private static final String INVALID_ARGUMENT = " Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        CartAddCommandParser c = new CartAddCommandParser();
        assertEquals(c.parse(VALID_INGREDIENT_ARGUMENT), new CartAddIngredientCommand(VALID_INGREDIENT));
        assertEquals(c.parse(VALID_RECIPE_INDEX_ARGUMENT), new CartAddRecipeIngredientCommand(VALID_RECIPE_INDEX));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        CartAddCommandParser c = new CartAddCommandParser();
        assertThrows(NullPointerException.class, () -> c.parse(null));
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () -> new CartAddCommandParser().parse(INVALID_ARGUMENT));
        assertThrows(ParseException.class, () ->
            new CartAddCommandParser().parse(INVALID_INGREDIENT_ARGUMENT_NO_QUANTITY));
        assertThrows(ParseException.class, () -> new CartAddCommandParser().parse(INVALID_INGREDIENT_ARGUMENT_NO_NAME));
        assertThrows(ParseException.class, ()
            -> new CartAddCommandParser().parse(INVALID_RECIPE_INDEX_ARGUMENT_NO_RECIPE));
    }
}
