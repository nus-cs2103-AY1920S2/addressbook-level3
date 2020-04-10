package team.easytravel.storage.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.activity.TypicalActivity.ACTIVITY_BUNGEE_JUMP;
import static team.easytravel.testutil.activity.TypicalActivity.ACTIVITY_FLY_KITE;
import static team.easytravel.testutil.activity.TypicalActivity.ACTIVITY_PEAK;
import static team.easytravel.testutil.activity.TypicalActivity.getTypicalActivityManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;

class JsonActivityStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonActivityStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readActivityManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readActivityManager(null));
    }

    private Optional<ReadOnlyActivityManager> readActivityManager(String filePath) throws Exception {
        return new JsonActivityStorage(Paths.get(filePath)).readActivityManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readActivityManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readActivityManager("notJsonFormatActivityManager.json"));
    }

    @Test
    public void readActivityManager_invalidActivityActivityManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readActivityManager("invalidActivityActivityManager.json"));
    }

    @Test
    public void readActivityManager_invalidAndValidActivityActivityManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readActivityManager(
                "invalidAndValidActivityActivityManager.json"));
    }

    @Test
    public void readAndSaveActivityManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempActivityManager.json");
        ActivityManager original = getTypicalActivityManager();
        JsonActivityStorage jsonActivityStorage = new JsonActivityStorage(filePath);

        //Save in new file and read back
        jsonActivityStorage.saveActivityManager(original, filePath);
        ReadOnlyActivityManager readBack = jsonActivityStorage.readActivityManager(filePath).get();
        assertEquals(original, new ActivityManager(readBack));

        //Modify data, overwrite exiting file and read back
        original.addActivity(ACTIVITY_FLY_KITE);
        original.removeActivity(ACTIVITY_PEAK);
        jsonActivityStorage.saveActivityManager(original, filePath);
        readBack = jsonActivityStorage.readActivityManager(filePath).get();
        assertEquals(original, new ActivityManager(readBack));

        // Save and read without specifying file path
        original.addActivity(ACTIVITY_BUNGEE_JUMP);
        jsonActivityStorage.saveActivityManager(original);
        readBack = jsonActivityStorage.readActivityManager().get();
        assertEquals(original, new ActivityManager(readBack));
    }

    @Test
    public void saveActivityManager_nullActivityManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveActivityManager(null, "SomeFile.json"));
    }

    /**
     * Saves {@code activityManager} at specified {@code filePath}.
     */
    private void saveActivityManager(ReadOnlyActivityManager activityManager, String filePath) {
        try {
            new JsonActivityStorage(Paths.get(filePath))
                    .saveActivityManager(activityManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file ", ioe);
        }
    }

    @Test
    public void saveActivityManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveActivityManager(new ActivityManager(), null));
    }


}
