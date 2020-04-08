package tatracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTrackerWithStudents;
import static tatracker.testutil.student.TypicalStudents.CS2030;
import static tatracker.testutil.student.TypicalStudents.CS3243;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tatracker.commons.exceptions.DataConversionException;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.TaTracker;

// import static tatracker.testutil.TypicalStudents.ALICE;
// import static tatracker.testutil.TypicalStudents.HOON;
// import static tatracker.testutil.TypicalStudents.IDA;

public class JsonTaTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTaTrackerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTaTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTaTracker(null));
    }

    private java.util.Optional<ReadOnlyTaTracker> readTaTracker(String filePath) throws Exception {
        return new JsonTaTrackerStorage(Paths.get(filePath)).readTaTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTaTracker("notJsonFormatTaTracker.json"));
    }

    @Test
    public void readTaTracker_invalidStudentTaTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTaTracker("invalidStudentTaTracker.json"));
    }

    @Test
    public void readTaTracker_invalidAndValidStudentTaTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTaTracker("invalidAndValidStudentTaTracker.json"));
    }

    @Test
    public void readAndSaveTaTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTaTracker.json");
        TaTracker original = getTypicalTaTrackerWithStudents();
        JsonTaTrackerStorage jsonTaTrackerStorage = new JsonTaTrackerStorage(filePath);

        // Save in new file and read back
        jsonTaTrackerStorage.saveTaTracker(original, filePath);
        ReadOnlyTaTracker readBack = jsonTaTrackerStorage.readTaTracker(filePath).get();
        assertEquals(original, new TaTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addModule(CS2030);
        original.deleteModule(CS3243);
        jsonTaTrackerStorage.saveTaTracker(original, filePath);
        readBack = jsonTaTrackerStorage.readTaTracker(filePath).get();
        assertEquals(original, new TaTracker(readBack));

        // Save and read without specifying file path
        original.addModule(CS3243);
        jsonTaTrackerStorage.saveTaTracker(original); // file path not specified
        readBack = jsonTaTrackerStorage.readTaTracker().get(); // file path not specified
        assertEquals(original, new TaTracker(readBack));

    }

    @Test
    public void saveTaTracker_nullTaTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code taTracker} at the specified {@code filePath}.
     */
    private void saveTaTracker(ReadOnlyTaTracker taTracker, String filePath) {
        try {
            new JsonTaTrackerStorage(Paths.get(filePath))
                    .saveTaTracker(taTracker, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTaTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaTracker(new TaTracker(), null));
    }
}
