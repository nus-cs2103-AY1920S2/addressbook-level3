package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilitators.ALICE;
import static seedu.address.testutil.TypicalFacilitators.HOON;
import static seedu.address.testutil.TypicalFacilitators.IDA;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ModManager;
import seedu.address.model.ReadOnlyModManager;

public class JsonModManagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonModManagerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readModManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readModManager(null));
    }

    private java.util.Optional<ReadOnlyModManager> readModManager(String filePath) throws Exception {
        return new JsonModManagerStorage(Paths.get(filePath)).readModManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readModManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readModManager("notJsonFormatModManager.json"));
    }

    @Test
    public void readModManager_invalidModuleModManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readModManager("invalidModuleModManager.json"));
    }

    @Test
    public void readModManager_invalidFacilitatorModManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readModManager("invalidFacilitatorModManager.json"));
    }

    @Test
    public void readModManager_invalidAndValidModuleModManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readModManager(
                "invalidAndValidModuleModManager.json"));
    }

    @Test
    public void readModManager_invalidAndValidFacilitatorModManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readModManager(
                "invalidAndValidFacilitatorModManager.json"));
    }

    @Test
    public void readAndSaveModManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempModManager.json");
        ModManager original = getTypicalModManager();
        JsonModManagerStorage jsonModManagerStorage = new JsonModManagerStorage(filePath);

        // Save in new file and read back
        jsonModManagerStorage.saveModManager(original, filePath);
        ReadOnlyModManager readBack = jsonModManagerStorage.readModManager(filePath).get();
        assertEquals(original, new ModManager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addFacilitator(HOON);
        original.removeFacilitator(ALICE);
        jsonModManagerStorage.saveModManager(original, filePath);
        readBack = jsonModManagerStorage.readModManager(filePath).get();
        assertEquals(original, new ModManager(readBack));

        // Save and read without specifying file path
        original.addFacilitator(IDA);
        jsonModManagerStorage.saveModManager(original); // file path not specified
        readBack = jsonModManagerStorage.readModManager().get(); // file path not specified
        assertEquals(original, new ModManager(readBack));

    }

    @Test
    public void saveModManager_nullModManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModManager(null, "SomeFile.json"));
    }

    /**
     * Saves {@code modManager} at the specified {@code filePath}.
     */
    private void saveModManager(ReadOnlyModManager modManager, String filePath) {
        try {
            new JsonModManagerStorage(Paths.get(filePath))
                    .saveModManager(modManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveModManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModManager(new ModManager(), null));
    }
}
