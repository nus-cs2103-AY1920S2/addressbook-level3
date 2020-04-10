package team.easytravel.storage.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.activity.TypicalActivity.getTypicalActivityManager;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.commons.util.JsonUtil;
import team.easytravel.model.listmanagers.ActivityManager;

class JsonSerializableActivityManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableActivityManagerTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalActivityActivityManager.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidActivityActivityManager.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateActivityActivityManager.json");

    @Test
    public void toModelType_typicalActivityFile_success() throws Exception {
        JsonSerializableActivityManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableActivityManager.class).get();
        ActivityManager activityManagerFromFile = dataFromFile.toModelType();
        ActivityManager typicalActivityActivityBook = getTypicalActivityManager();
        assertEquals(activityManagerFromFile, typicalActivityActivityBook);
    }

    @Test
    public void toModelType_invalidActivityFile_throwsIllegalValueException() throws Exception {
        JsonSerializableActivityManager dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableActivityManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateActivities_throwsIllegalValueException() throws Exception {
        JsonSerializableActivityManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableActivityManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableActivityManager.MESSAGE_DUPLICATE_ACTIVITY,
                dataFromFile::toModelType);
    }


}
