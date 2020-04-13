package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Cart;
import seedu.address.testutil.TypicalIngredients;

public class JsonSerializableCartTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonSerializableIngredientListTest");
    private static final Path TYPICAL_INGREDIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalIngredients.json");
    private static final Path INVALID_INGREDIENT_FILE = TEST_DATA_FOLDER.resolve("invalidIngredient.json");
    private static final Path DUPLICATE_INGREDIENTS_FILE = TEST_DATA_FOLDER.resolve("duplicateIngredients.json");

    @Test
    public void toModelType_typicalIngredientsFile_success() throws Exception {
        JsonSerializableCart dataFromFile = JsonUtil.readJsonFile(TYPICAL_INGREDIENTS_FILE,
                JsonSerializableCart.class).get();
        Cart cartFromFile = dataFromFile.toModelType();
        Cart typicalIngredientsCart = TypicalIngredients.getTypicalCart();
        assertEquals(cartFromFile, typicalIngredientsCart);
    }

    @Test
    public void toModelType_invalidIngredientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCart dataFromFile = JsonUtil.readJsonFile(INVALID_INGREDIENT_FILE,
                JsonSerializableCart.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateIngredients_throwsIllegalValueException() throws Exception {
        JsonSerializableCart dataFromFile = JsonUtil.readJsonFile(DUPLICATE_INGREDIENTS_FILE,
                JsonSerializableCart.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCart.MESSAGE_DUPLICATE_INGREDIENT,
                dataFromFile::toModelType);
    }

}
