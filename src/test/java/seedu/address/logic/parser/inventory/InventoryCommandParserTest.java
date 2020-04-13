package seedu.address.logic.parser.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.inventory.InventoryAddIngredientCommand;
import seedu.address.logic.commands.inventory.InventoryRemoveIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

public class InventoryCommandParserTest {
    private static final Ingredient VALID_INGREDIENT = new Ingredient(new IngredientName("Ingredient"),
            new IngredientQuantity("5"));
    private static final IngredientName VALID_INGREDIENT_NAME = new IngredientName("Ingredient");
    private static final IngredientQuantity VALID_INGREDIENT_QUANTITY = new IngredientQuantity("5");
    private static final String VALID_ADD_INGREDIENT_ARGUMENT = " add ingredient i/Ingredient q/5";
    private static final String VALID_REMOVE_INGREDIENT_ARGUMENT = " remove ingredient i/Ingredient q/5";
    private static final String VALID_REMOVE_INGREDIENT_ARGUMENT_NO_QUANTITY = " remove ingredient i/Ingredient";
    private static final String INVALID_ADD_INGREDIENT_ARGUMENT_NO_NAME = " add q/5";
    private static final String INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_NAME = " remove q/5";
    private static final String INVALID_ARGUMENT = " Invalid argument";

    @Test
    public void parse_validInput() throws ParseException {
        InventoryCommandParser c = new InventoryCommandParser();
        assertEquals(c.parse(VALID_ADD_INGREDIENT_ARGUMENT), new InventoryAddIngredientCommand(VALID_INGREDIENT));
        assertEquals(c.parse(VALID_REMOVE_INGREDIENT_ARGUMENT),
                new InventoryRemoveIngredientCommand(VALID_INGREDIENT_NAME, Optional.of(VALID_INGREDIENT_QUANTITY)));
        assertEquals(c.parse(VALID_REMOVE_INGREDIENT_ARGUMENT_NO_QUANTITY),
                new InventoryRemoveIngredientCommand(VALID_INGREDIENT_NAME, Optional.empty()));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InventoryCommandParser().parse(null));
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () ->
                new InventoryCommandParser().parse(INVALID_ADD_INGREDIENT_ARGUMENT_NO_NAME));
        assertThrows(ParseException.class, () ->
                new InventoryCommandParser().parse(INVALID_REMOVE_INGREDIENT_ARGUMENT_NO_NAME));
        assertThrows(ParseException.class, () -> new InventoryCommandParser().parse(INVALID_ARGUMENT));
    }
}
