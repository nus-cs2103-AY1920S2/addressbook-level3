package seedu.eylah.expensesplitter.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.eylah.expensesplitter.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.commons.storage.JsonUserPrefsStorage;

public class JsonUserPrefsSplitterStorageTest {

    private static final Path TEST_DATA_FOLDER =
        Paths.get("src", "test", "data", "JsonUserPrefsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readUserPrefs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readUserPrefs(null));
    }

    private Optional<UserPrefs> readUserPrefs(String userPrefsFileInTestDataFolder) throws DataConversionException {
        Path prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new JsonUserPrefsStorage(prefsFilePath).readUserPrefs(prefsFilePath);
    }

    @Test
    public void readUserPrefs_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readUserPrefs("NonExistentFile.json").isPresent());
    }

    @Test
    public void readUserPrefs_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readUserPrefs("NotJsonFormatUserPrefs.json"));
    }

    private Path addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(userPrefsFileInTestDataFolder)
            : null;
    }



    @Test
    public void readUserPrefs_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        UserPrefs actual = readUserPrefs("EmptyUserPrefs.json").get();
        assertEquals(new UserPrefs(), actual);
    }





    @Test
    public void savePrefs_nullPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserPrefs(null, "SomeFile.json"));
    }

    @Test
    public void saveUserPrefs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserPrefs(new UserPrefs(), null));
    }

    /**
     * Saves {@code userPrefs} at the specified {@code prefsFileInTestDataFolder} filepath.
     */
    private void saveUserPrefs(UserPrefs userPrefs, String prefsFileInTestDataFolder) {
        try {
            new JsonUserPrefsStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder))
                .saveUserPrefs(userPrefs);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

}
