package seedu.zerotoone.storage.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.zerotoone.testutil.log.TypicalLogs.ARMS_WORKOUT;
import static seedu.zerotoone.testutil.log.TypicalLogs.getTypicalLogList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.log.LogList;
import seedu.zerotoone.model.log.ReadOnlyLogList;

class LogListStorageManagerTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "log",
        "LogListStorageManagerTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readLogList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readLogList(null));
    }

    private Optional<ReadOnlyLogList> readLogList(String filePath) throws Exception {
        return new LogListStorageManager(Paths.get(filePath))
            .readLogList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readLogList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readLogList("notJsonFormatLogList.json"));
    }

    @Test
    public void readLogList_invalidLogList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLogList("invalidLogList.json"));
    }

    @Test
    public void readLogList_invalidAndValidLogList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readLogList("invalidAndValidLogList.json"));
    }

    @Test
    public void readAndSaveLogList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempLogList.json");
        LogList original = getTypicalLogList();
        LogListStorageManager jsonLogListStorage = new LogListStorageManager(filePath);

        // Save in new file and read back
        jsonLogListStorage.saveLogList(original, filePath);
        ReadOnlyLogList readBack = jsonLogListStorage.readLogList(filePath).get();
        assertEquals(original.getLogList().size(), new LogList(readBack).getLogList().size());

        // Modify data, overwrite exiting file, and read back
        original.removeCompletedWorkout(0);
        jsonLogListStorage.saveLogList(original, filePath);
        readBack = jsonLogListStorage.readLogList(filePath).get();
        assertEquals(original.getLogList().size(), new LogList(readBack).getLogList().size());

        // Save and read without specifying file path
        original.addCompletedWorkout(ARMS_WORKOUT);
        jsonLogListStorage.saveLogList(original); // file path not specified
        readBack = jsonLogListStorage.readLogList().get(); // file path not specified
        assertEquals(original.getLogList().size(), new LogList(readBack).getLogList().size());

    }

    @Test
    public void saveLogList_nullLogList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLogList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code logList} at the specified {@code filePath}.
     */
    private void saveLogList(ReadOnlyLogList logList, String filePath) {
        try {
            new LogListStorageManager(Paths.get(filePath))
                .saveLogList(logList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveLogList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLogList(new LogList(), null));
    }

}
