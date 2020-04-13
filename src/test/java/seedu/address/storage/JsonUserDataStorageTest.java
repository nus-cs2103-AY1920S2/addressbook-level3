package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalUser.BOB;
import static seedu.address.testutil.TypicalUser.getTypicalUserData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserData;
import seedu.address.model.UserData;

public class JsonUserDataStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonUserDataStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readUserData_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readUserData(null));
    }

    private java.util.Optional<ReadOnlyUserData> readUserData(String filePath) throws Exception {
        return new JsonUserDataStorage(Paths.get(filePath)).readUserData(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readUserData("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readUserData("notJsonFormatUserData.json"));
    }

    @Test
    public void readUserData_invalidUserData_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readUserData("invalidPersonUserData.json"));
    }

    @Test
    public void readAndSaveUserData_success() throws Exception {
        Path filePath = testFolder.resolve("TempUserData.json");
        UserData original = getTypicalUserData();
        JsonUserDataStorage jsonUserDataStorage = new JsonUserDataStorage(filePath);

        // Save in new file and read back
        jsonUserDataStorage.saveUserData(original, filePath);
        ReadOnlyUserData readBack = jsonUserDataStorage.readUserData(filePath).get();
        assertEquals(original, new UserData(readBack));

        // Modify data, overwrite exiting file, and read back
        UserData another = new UserData(BOB);
        jsonUserDataStorage.saveUserData(another, filePath);
        readBack = jsonUserDataStorage.readUserData(filePath).get();
        assertEquals(another, new UserData(readBack));

        // Save and read without specifying file path
        jsonUserDataStorage.saveUserData(original); // file path not specified
        readBack = jsonUserDataStorage.readUserData().get(); // file path not specified
        assertEquals(original, new UserData(readBack));

    }

    @Test
    public void saveUserData_nullUserData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserData(null, "SomeFile.json"));
    }

    /**
     * Saves {@code userData} at the specified {@code filePath}.
     */
    private void saveUserData(ReadOnlyUserData userData, String filePath) {
        try {
            new JsonUserDataStorage(Paths.get(filePath))
                    .saveUserData(userData, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }
}
