package seedu.eylah.diettracker.storage;

// import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.commons.util.JsonUtil;
// import seedu.eylah.diettracker.model.FoodBook;
// import seedu.eylah.diettracker.testutil.TypicalFood;


public class JsonSerializableFoodBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFoodBookTest");
    // private static final Path TYPICAL_FOODS_FILE = TEST_DATA_FOLDER.resolve("typicalFoodFoodBook.json");
    private static final Path INVALID_FOOD_FILE = TEST_DATA_FOLDER.resolve("invalidFoodFoodBook.json");
    // private static final Path DUPLICATE_FOOD_FILE = TEST_DATA_FOLDER.resolve("duplicateFoodFoodBook.json");

    // @Test
    // public void toModelType_typicalFoodFile_success() throws Exception {
    //     JsonSerializableFoodBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_FOODS_FILE,
    //             JsonSerializableFoodBook.class).get();
    //     FoodBook foodBookFromFile = dataFromFile.toModelType();
    //     FoodBook typicalFoodFoodBook = TypicalFood.getTypicalFoodBook();

    //     assertEquals(foodBookFromFile, typicalFoodFoodBook);
    // }

    @Test
    public void toModelType_invalidFoodFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFoodBook dataFromFile = JsonUtil.readJsonFile(INVALID_FOOD_FILE,
                JsonSerializableFoodBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    // @Test
    // public void toModelType_duplicateFood_throwsIllegalValueException() throws Exception {
    //     JsonSerializableFoodBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_FOOD_FILE,
    //             JsonSerializableFoodBook.class).get();
    //     assertThrows(IllegalValueException.class, JsonSerializableFoodBook.MESSAGE_DUPLICATE_FOOD,
    //             dataFromFile::toModelType);
    // }
}
