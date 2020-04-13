package seedu.address.storage;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
//import seedu.address.model.Cookbook;
//import seedu.address.testutil.TypicalRecipes;

public class JsonSerializableCookbookTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonSerializableCookbookTest");
    //private static final Path TYPICAL_RECIPES_FILE = TEST_DATA_FOLDER.resolve("typicalRecipes.json");
    private static final Path INVALID_RECIPE_FILE = TEST_DATA_FOLDER.resolve("invalidRecipe.json");
    private static final Path DUPLICATE_RECIPES_FILE = TEST_DATA_FOLDER.resolve("duplicateRecipes.json");

    //@Test
    //public void toModelType_typicalRecipesFile_success() throws Exception {
    //    JsonSerializableCookbook dataFromFile = JsonUtil.readJsonFile(TYPICAL_RECIPES_FILE,
    //            JsonSerializableCookbook.class).get();
    //    Cookbook cookbookFromFile = dataFromFile.toModelType();
    //    Cookbook typicalRecipesCookbook = TypicalRecipes.getTypicalCookbook();
    //    assertEquals(cookbookFromFile, typicalRecipesCookbook);
    //}

    @Test
    public void toModelType_invalidRecipeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCookbook dataFromFile = JsonUtil.readJsonFile(INVALID_RECIPE_FILE,
                JsonSerializableCookbook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateRecipes_throwsIllegalValueException() throws Exception {
        JsonSerializableCookbook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_RECIPES_FILE,
                JsonSerializableCookbook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCookbook.MESSAGE_DUPLICATE_RECIPE,
                dataFromFile::toModelType);
    }
}
