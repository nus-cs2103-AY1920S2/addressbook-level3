package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Inventory;
import seedu.address.testutil.TypicalIngredients;

public class JsonSerializableInventoryTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonSerializableIngredientListTest");
    private static final Path TYPICAL_INGREDIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalIngredients.json");
    private static final Path INVALID_INGREDIENT_FILE = TEST_DATA_FOLDER.resolve("invalidIngredient.json");
    private static final Path DUPLICATE_INGREDIENTS_FILE = TEST_DATA_FOLDER.resolve("duplicateIngredients.json");

    @Test
    public void toModelType_typicalIngredientsFile_success() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(TYPICAL_INGREDIENTS_FILE,
                JsonSerializableInventory.class).get();
        Inventory inventoryFromFile = dataFromFile.toModelType();
        Inventory typicalIngredientsInventory = TypicalIngredients.getTypicalInventory();
        assertEquals(inventoryFromFile, typicalIngredientsInventory);
    }

    @Test
    public void toModelType_invalidIngredientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(INVALID_INGREDIENT_FILE,
                JsonSerializableInventory.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateIngredients_throwsIllegalValueException() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(DUPLICATE_INGREDIENTS_FILE,
                JsonSerializableInventory.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableInventory.MESSAGE_DUPLICATE_INGREDIENT,
                dataFromFile::toModelType);
    }

}
