package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.UserData;
import seedu.address.testutil.TypicalUser;

public class JsonSerializableUserDataTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableUserDataTest");
    private static final Path TYPICAL_USER_FILE = TEST_DATA_FOLDER.resolve("typicalUserData.json");
    private static final Path INVALID_USER_FILE = TEST_DATA_FOLDER.resolve("invalidUserData.json");

    @Test
    public void toModelType_typicalUserFile_success() throws Exception {
        JsonSerializableUserData dataFromFile = JsonUtil.readJsonFile(TYPICAL_USER_FILE,
                JsonSerializableUserData.class).get();
        UserData userDataFromFile = dataFromFile.toModelType();
        UserData typicalUserData = TypicalUser.getTypicalUserData();
        assertEquals(userDataFromFile, typicalUserData);
    }

    @Test
    public void toModelType_invalidUserData_throwsIllegalValueException() throws Exception {
        JsonSerializableUserData dataFromFile = JsonUtil.readJsonFile(INVALID_USER_FILE,
                JsonSerializableUserData.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
