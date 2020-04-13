package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedIngredient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIngredients.BUTTER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;

public class JsonAdaptedIngredientTest {
    private static final String INVALID_INGREDIENT_NAME = "R!ce";
    private static final String INVALID_INGREDIENT_QUANTITY = "one";

    private static final String VALID_INGREDIENT_NAME = BUTTER.getName().toString();
    private static final String VALID_INGREDIENT_QUANTITY = BUTTER.getQuantity().toString();

    @Test
    public void toModelType_validIngredientDetails_returnsIngredient() throws Exception {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(BUTTER);
        assertEquals(BUTTER, ingredient.toModelType());
    }

    @Test
    public void toModelType_invalidIngredientName_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(INVALID_INGREDIENT_NAME, VALID_INGREDIENT_QUANTITY);
        String expectedMessage = IngredientName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullIngredientName_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(null, VALID_INGREDIENT_QUANTITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IngredientName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_invalidIngredientQuantity_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient =
                new JsonAdaptedIngredient(VALID_INGREDIENT_NAME, INVALID_INGREDIENT_QUANTITY);
        String expectedMessage = IngredientQuantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

    @Test
    public void toModelType_nullIngredientQuantity_throwsIllegalValueException() {
        JsonAdaptedIngredient ingredient = new JsonAdaptedIngredient(VALID_INGREDIENT_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, IngredientQuantity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, ingredient::toModelType);
    }

}
