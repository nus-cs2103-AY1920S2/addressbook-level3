package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;

//@@author chanckben
public class JsonSerializableProfileListTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableProfileListTest");
    private static final Path INVALID_PROFILE_FILE = TEST_DATA_FOLDER.resolve("invalidProfileProfileList.json");
    private static final Path DUPLICATE_PROFILE_FILE = TEST_DATA_FOLDER.resolve("duplicateProfileProfileList.json");

    @Test
    public void toModelType_invalidProfileFile_throwsIllegalValueException() throws Exception {
        JsonSerializableProfileList dataFromFile = JsonUtil.readJsonFile(INVALID_PROFILE_FILE,
                JsonSerializableProfileList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateProfile_throwsIllegalValueException() throws Exception {
        JsonSerializableProfileList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PROFILE_FILE,
                JsonSerializableProfileList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableProfileList.MESSAGE_DUPLICATE_PROFILE,
                dataFromFile::toModelType);
    }
}
