package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.CS2103_QUIZ;
import static seedu.address.testutil.TypicalAssignments.IS1103_QUIZ;
import static seedu.address.testutil.TypicalAssignments.getTypicalScheduler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AssignmentSchedule;
import seedu.address.model.ReadOnlyAssignmentSchedule;

public class JsonAssignmentScheduleStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAssignmentScheduleStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readScheduler_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readScheduler(null));
    }

    private java.util.Optional<ReadOnlyAssignmentSchedule> readScheduler(String filePath) throws Exception {
        return new JsonAssignmentScheduleStorage(Paths.get(filePath))
            .readScheduler(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readScheduler("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readScheduler("notJsonFormatScheduler.json"));
    }

    @Test
    public void readScheduler_invalidAssignmentScheduler_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readScheduler("invalidAssignmentScheduler.json"));
    }

    @Test
    public void readScheduler_invalidAndValidAssignmentScheduler_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readScheduler("invalidAndValidAssignmentScheduler.json"));
    }

    @Test
    public void readAndSaveScheduler_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempScheduler.json");
        AssignmentSchedule original = getTypicalScheduler();
        JsonAssignmentScheduleStorage jsonAssignmentScheduleStorage = new JsonAssignmentScheduleStorage(filePath);

        // Save in new file and read back
        jsonAssignmentScheduleStorage.saveScheduler(original, filePath);
        ReadOnlyAssignmentSchedule readBack = jsonAssignmentScheduleStorage.readScheduler(filePath).get();
        assertEquals(original, new AssignmentSchedule(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addAssignment(IS1103_QUIZ);
        jsonAssignmentScheduleStorage.saveScheduler(original, filePath);
        readBack = jsonAssignmentScheduleStorage.readScheduler(filePath).get();
        assertEquals(original, new AssignmentSchedule(readBack));

        // Save and read without specifying file path
        original.addAssignment(CS2103_QUIZ);
        jsonAssignmentScheduleStorage.saveScheduler(original); // file path not specified
        readBack = jsonAssignmentScheduleStorage.readScheduler().get(); // file path not specified
        assertEquals(original, new AssignmentSchedule(readBack));
    }

    @Test
    public void saveScheduler_nullScheduler_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveScheduler(null, "SomeFile.json"));
    }

    /**
     * Saves {@code scheduler} at the specified {@code filePath}.
     */
    private void saveScheduler(ReadOnlyAssignmentSchedule scheduler, String filePath) {
        try {
            new JsonAssignmentScheduleStorage(Paths.get(filePath))
                    .saveScheduler(scheduler, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveScheduler_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveScheduler(new AssignmentSchedule(), null));
    }
}
