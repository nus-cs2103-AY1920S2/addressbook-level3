package seedu.address.logic.parser.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.inventory.InventoryRemoveIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

public class InventoryRemoveIngredientCommandParserTest {
    private static final IngredientName VALID_INGREDIENT_NAME = new IngredientName("Ingredient");
    private static final IngredientQuantity VALID_INGREDIENT_QUANTITY = new IngredientQuantity("5");
    private static final String INGREDIENT_KEYWORD = "ingredient";
    private static final String VALID_INGREDIENT_ARGUMENT = INGREDIENT_KEYWORD + " i/Ingredient q/5";
    private static final String VALID_INGREDIENT_ARGUMENT_NO_QUANTITY = INGREDIENT_KEYWORD + " i/Ingredient";
    private static final String INVALID_INGREDIENT_ARGUMENT_NO_NAME = INGREDIENT_KEYWORD + " q/5";
    private static final String INVALID_ARGUMENT = INGREDIENT_KEYWORD + "Invalid argument";

    @Test
    public void parse_validInputWithQuantity() throws ParseException {
        InventoryRemoveIngredientCommandParser c = new InventoryRemoveIngredientCommandParser();
        assertEquals(c.parse(VALID_INGREDIENT_ARGUMENT),
                new InventoryRemoveIngredientCommand(VALID_INGREDIENT_NAME, Optional.of(VALID_INGREDIENT_QUANTITY)));
    }

    @Test
    public void parse_validInputWithoutQuantity() throws ParseException {
        InventoryRemoveIngredientCommandParser c = new InventoryRemoveIngredientCommandParser();
        assertEquals(c.parse(VALID_INGREDIENT_ARGUMENT_NO_QUANTITY),
                new InventoryRemoveIngredientCommand(VALID_INGREDIENT_NAME, Optional.empty()));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        InventoryRemoveIngredientCommandParser c = new InventoryRemoveIngredientCommandParser();
        assertThrows(NullPointerException.class, () -> c.parse(null));
    }

    @Test
    public void parse_invalidInput() {
        assertThrows(ParseException.class, () -> new InventoryRemoveIngredientCommandParser().parse(INVALID_ARGUMENT));
        assertThrows(ParseException.class, () ->
                new InventoryRemoveIngredientCommandParser().parse(INVALID_INGREDIENT_ARGUMENT_NO_NAME));
    }
}
