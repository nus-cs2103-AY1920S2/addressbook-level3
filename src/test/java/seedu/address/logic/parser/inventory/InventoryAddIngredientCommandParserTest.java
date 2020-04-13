package seedu.address.logic.parser.inventory;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.inventory.InventoryAddIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

public class InventoryAddIngredientCommandParserTest {
    private static final Ingredient VALID_INGREDIENT = new Ingredient(new IngredientName("Ingredient"),
            new IngredientQuantity("5"));
    private static final String INGREDIENT_KEYWORD = "ingredient";
    private static final String VALID_INGREDIENT_ARGUMENT = INGREDIENT_KEYWORD + " i/Ingredient q/5";
    private static final String INVALID_INGREDIENT_ARGUMENT_NO_NAME = INGREDIENT_KEYWORD + " q/5";
    private static final String INVALID_ARGUMENT = INGREDIENT_KEYWORD + " Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        InventoryAddIngredientCommandParser c = new InventoryAddIngredientCommandParser();
        Assertions.assertEquals(c.parse(VALID_INGREDIENT_ARGUMENT),
            new InventoryAddIngredientCommand(VALID_INGREDIENT));
    }

    @Test
    public void parse_null_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InventoryAddIngredientCommandParser().parse(null));
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, ()
            -> new InventoryAddIngredientCommandParser().parse(INVALID_INGREDIENT_ARGUMENT_NO_NAME));
        assertThrows(ParseException.class, ()
            -> new InventoryAddIngredientCommandParser().parse(INVALID_ARGUMENT));
    }
}
