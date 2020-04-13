package seedu.foodiebot.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.foodiebot.commons.util.JsonUtil;
import seedu.foodiebot.model.FoodieBot;
import seedu.foodiebot.testutil.TypicalCanteens;

public class JsonSerializableFoodieBotTest {


    private static final Path TEST_DATA_FOLDER =
        Paths.get("src", "test", "data", "JsonSerializableFoodieBotTest");
    private static final Path TYPICAL_CANTEENS_FILE =
        TEST_DATA_FOLDER.resolve("typicalCanteensFoodieBot.json");
    private static final Path INVALID_PERSON_FILE =
        TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE =
        TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalCanteensFile_success() throws Exception {
        JsonSerializableFoodieBot dataFromFile =
            JsonUtil.readJsonFile(TYPICAL_CANTEENS_FILE, JsonSerializableFoodieBot.class)
                .get();
        FoodieBot foodieBotFromFile = dataFromFile.toModelType();
        FoodieBot typicalCanteensFoodieBot = TypicalCanteens.getTypicalFoodieBot();
        assertEquals(foodieBotFromFile, typicalCanteensFoodieBot);
    }

    /*
    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile =
                JsonUtil.readJsonFile(INVALID_PERSON_FILE, JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile =
                JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE, JsonSerializableAddressBook.class)
                        .get();
        assertThrows(
                IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

     */
}
