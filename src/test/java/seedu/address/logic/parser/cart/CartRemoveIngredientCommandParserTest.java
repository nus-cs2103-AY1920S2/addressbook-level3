package seedu.address.logic.parser.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cart.CartRemoveIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

public class CartRemoveIngredientCommandParserTest {
    private static final IngredientName VALID_INGREDIENT_NAME = new IngredientName("Ingredient");
    private static final IngredientQuantity VALID_INGREDIENT_QUANTITY = new IngredientQuantity("5");

    private static final String INGREDIENT_KEYWORD = "ingredient";
    private static final String VALID_INGREDIENT_ARGUMENT = INGREDIENT_KEYWORD + " i/Ingredient q/5";
    private static final String VALID_INGREDIENT_ARGUMENT_NO_QUANTITY = INGREDIENT_KEYWORD + " i/Ingredient";
    private static final String INVALID_INGREDIENT_ARGUMENT_NO_NAME = INGREDIENT_KEYWORD + " q/5";
    private static final String INVALID_ARGUMENT = INGREDIENT_KEYWORD + "Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        CartRemoveIngredientCommandParser c = new CartRemoveIngredientCommandParser();
        assertEquals(c.parse(VALID_INGREDIENT_ARGUMENT),
                new CartRemoveIngredientCommand(VALID_INGREDIENT_NAME, Optional.of(VALID_INGREDIENT_QUANTITY)));
    }

    @Test
    public void parse_validInputWithoutQuantity() throws ParseException {
        CartRemoveIngredientCommandParser c = new CartRemoveIngredientCommandParser();
        assertEquals(c.parse(VALID_INGREDIENT_ARGUMENT_NO_QUANTITY),
                new CartRemoveIngredientCommand(VALID_INGREDIENT_NAME, Optional.empty()));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        CartRemoveIngredientCommandParser c = new CartRemoveIngredientCommandParser();
        assertThrows(NullPointerException.class, () -> c.parse(null));
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () -> new CartRemoveIngredientCommandParser().parse(INVALID_ARGUMENT));
        assertThrows(ParseException.class, () ->
                new CartRemoveIngredientCommandParser().parse(INVALID_INGREDIENT_ARGUMENT_NO_NAME));
    }
}
