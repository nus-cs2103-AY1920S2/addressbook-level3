package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.CS2103_QUIZ;
import static seedu.address.testutil.TypicalAssignments.IS1103_QUIZ;
import static seedu.address.testutil.TypicalAssignments.getTypicalSchoolworkTracker;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySchoolworkTracker;
import seedu.address.model.SchoolworkTracker;

public class JsonSchoolworkTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
        "JsonSchoolworkTrackerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readSchoolworkTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readSchoolworkTracker(null));
    }

    private java.util.Optional<ReadOnlySchoolworkTracker> readSchoolworkTracker(String filePath) throws Exception {
        return new JsonSchoolworkTrackerStorage(Paths.get(filePath))
            .readSchoolworkTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readSchoolworkTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readSchoolworkTracker("notJsonFormatSchoolworkTracker.json"));
    }

    @Test
    public void readSchoolworkTracker_invalidAssignmentScheduler_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readSchoolworkTracker("invalidSchoolworkTracker.json"));
    }

    @Test
    public void readSchoolworkTracker_invalidAndValidSchoolworkTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readSchoolworkTracker("invalidAndValidSchoolworkTracker.json"));
    }

    @Test
    public void readAndSaveSchoolworkTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempSchoolworkTracker.json");
        SchoolworkTracker original = getTypicalSchoolworkTracker();
        JsonSchoolworkTrackerStorage jsonAssignmentScoolworkTrackerStorage = new JsonSchoolworkTrackerStorage(filePath);

        // Save in new file and read back
        jsonAssignmentScoolworkTrackerStorage.saveSchoolworkTracker(original, filePath);
        ReadOnlySchoolworkTracker readBack =
            jsonAssignmentScoolworkTrackerStorage.readSchoolworkTracker(filePath).get();
        assertEquals(original, new SchoolworkTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addAssignment(IS1103_QUIZ);
        jsonAssignmentScoolworkTrackerStorage.saveSchoolworkTracker(original, filePath);
        readBack = jsonAssignmentScoolworkTrackerStorage.readSchoolworkTracker(filePath).get();
        assertEquals(original, new SchoolworkTracker(readBack));

        // Save and read without specifying file path
        original.addAssignment(CS2103_QUIZ);
        jsonAssignmentScoolworkTrackerStorage.saveSchoolworkTracker(original); // file path not specified
        readBack = jsonAssignmentScoolworkTrackerStorage.readSchoolworkTracker().get(); // file path not specified
        assertEquals(original, new SchoolworkTracker(readBack));
    }

    @Test
    public void saveSchoolworkTracker_nullSchoolworkTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSchoolworkTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code schoolwork Tracker} at the specified {@code filePath}.
     */
    private void saveSchoolworkTracker(ReadOnlySchoolworkTracker schoolworkTracker, String filePath) {
        try {
            new JsonSchoolworkTrackerStorage(Paths.get(filePath))
                    .saveSchoolworkTracker(schoolworkTracker, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveSchoolworkTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSchoolworkTracker(new SchoolworkTracker(), null));
    }
}
