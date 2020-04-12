package seedu.recipe.model.recipe.ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class QuantityTest {

    private static final Unit VALID_UNIT = Unit.GRAM;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Quantity(0, null)); // null unit
    }

    @Test
    public void constructor_invalidValues_throwsIllegalArgumentException() {
        // invalid negative magnitude
        assertThrows(IllegalArgumentException.class, () -> new Quantity(-1, VALID_UNIT));

        // invalid zero magnitude
        assertThrows(IllegalArgumentException.class, () -> new Quantity(0, VALID_UNIT));

        // invalid magnitude (too large)
        assertThrows(IllegalArgumentException.class, () -> new Quantity(10000, VALID_UNIT));
        assertThrows(IllegalArgumentException.class, () -> new Quantity(10001, VALID_UNIT));
    }

    @Test
    public void addQuantityInGram_sameUnit_success() {
        double validMagnitude = 300;
        Quantity validQuantityOne = new Quantity(validMagnitude, VALID_UNIT);
        Quantity validQuantityTwo = new Quantity(validMagnitude, VALID_UNIT);
        MainIngredientType validMainIngredientType = MainIngredientType.VEGETABLE;

        Quantity expectedQuantity = new Quantity(validMagnitude + validMagnitude, VALID_UNIT);
        Quantity addedQuantities = validQuantityOne.addQuantityInGram(validQuantityTwo, validMainIngredientType);
        assertEquals(expectedQuantity, addedQuantities);
    }

    @Test
    public void isValidQuantity() {
        // invalid quantity
        assertFalse(Quantity.isValidQuantity(0)); // zero value
        assertFalse(Quantity.isValidQuantity(-1)); // negative value
        assertFalse(Quantity.isValidQuantity(10000)); // too large value
        assertFalse(Quantity.isValidQuantity(10001)); // too large value

        // valid quantity
        assertTrue(Quantity.isValidQuantity(1));
        assertTrue(Quantity.isValidQuantity(9999));
    }
}
