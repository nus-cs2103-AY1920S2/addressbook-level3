package seedu.address.logic.parser.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cart.CartAddIngredientCommand;
import seedu.address.logic.commands.cart.CartAddRecipeIngredientCommand;
import seedu.address.logic.commands.cart.CartClearCommand;
import seedu.address.logic.commands.cart.CartMoveCommand;
import seedu.address.logic.commands.cart.CartRemoveIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

public class CartCommandParserTest {
    private static final Ingredient VALID_INGREDIENT = new Ingredient(new IngredientName("Ingredient"),
            new IngredientQuantity("5"));
    private static final IngredientName VALID_INGREDIENT_NAME = new IngredientName("Ingredient");
    private static final IngredientQuantity VALID_INGREDIENT_QUANTITY = new IngredientQuantity("5");
    private static final String VALID_ADD_INGREDIENT_ARGUMENT = " add ingredient i/Ingredient q/5";
    private static final Index VALID_RECIPE_INDEX = new Index(0);
    private static final String VALID_ADD_RECIPE_ARGUMENT = "add recipe 1";
    private static final String VALID_CLEAR_ARGUMENT = "clear";
    private static final String VALID_MOVE_ARGUMENT = "move";
    private static final String VALID_REMOVE_INGREDIENT_ARGUMENT = "remove ingredient i/Ingredient q/5";
    private static final String VALID_REMOVE_INGREDIENT_ARGUMENT_NO_QUANTITY = " remove ingredient i/Ingredient";
    private static final String INVALID_ADD_INGREDIENT_ARGUMENT_NO_NAME = " add q/5";
    private static final String INVALID_CLEAR_ARGUMENT = "clear clear";
    private static final String INVALID_MOVE_ARGUMENT = "move move";
    private static final String INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_NAME = " remove q/5";
    private static final String INVALID_ARGUMENT = "Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        CartCommandParser c = new CartCommandParser();
        assertEquals(c.parse(VALID_ADD_INGREDIENT_ARGUMENT), new CartAddIngredientCommand(VALID_INGREDIENT));
        assertEquals(c.parse(VALID_ADD_RECIPE_ARGUMENT), new CartAddRecipeIngredientCommand(VALID_RECIPE_INDEX));
        assertEquals(c.parse(VALID_CLEAR_ARGUMENT), new CartClearCommand());
        assertEquals(c.parse(VALID_MOVE_ARGUMENT), new CartMoveCommand());
        assertEquals(c.parse(VALID_REMOVE_INGREDIENT_ARGUMENT),
                new CartRemoveIngredientCommand(VALID_INGREDIENT_NAME, Optional.of(VALID_INGREDIENT_QUANTITY)));
        assertEquals(c.parse(VALID_REMOVE_INGREDIENT_ARGUMENT_NO_QUANTITY),
                new CartRemoveIngredientCommand(VALID_INGREDIENT_NAME, Optional.empty()));
    }

    @Test
    public void parse_null_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CartCommandParser().parse(null));
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () ->
                new CartCommandParser().parse(INVALID_ADD_INGREDIENT_ARGUMENT_NO_NAME));
        assertThrows(ParseException.class, () ->
                new CartCommandParser().parse(INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_NAME));
        assertThrows(ParseException.class, () -> new CartCommandParser().parse(INVALID_ARGUMENT));
        assertThrows(ParseException.class, () -> new CartCommandParser().parse(INVALID_CLEAR_ARGUMENT));
        assertThrows(ParseException.class, () -> new CartCommandParser().parse(INVALID_MOVE_ARGUMENT));
    }
}
