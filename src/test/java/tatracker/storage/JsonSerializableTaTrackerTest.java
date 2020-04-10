package tatracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tatracker.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.commons.util.JsonUtil;
import tatracker.model.TaTracker;
import tatracker.model.util.SampleDataUtil;

public class JsonSerializableTaTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTaTrackerTest");
    private static final Path TYPICAL_SAVE_FILE = TEST_DATA_FOLDER.resolve("typicalTaTracker.json");

    private static final Path DUPLICATE_MODULES_FILE =
            TEST_DATA_FOLDER.resolve("duplicateModulesTaTracker.json");
    private static final Path DUPLICATE_SESSIONS_FILE =
            TEST_DATA_FOLDER.resolve("duplicateSessionsTaTracker.json");
    private static final Path DUPLICATE_DONE_SESSIONS_FILE =
            TEST_DATA_FOLDER.resolve("duplicateDoneSessionsTaTracker.json");

    @Test
    public void toModelType_typicalSaveFile_success() throws Exception {
        JsonSerializableTaTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_SAVE_FILE,
                JsonSerializableTaTracker.class).orElseThrow(NoSuchElementException::new);
        TaTracker taTrackerFromFile = dataFromFile.toModelType();
        TaTracker typicalStudentsTaTracker = new TaTracker(SampleDataUtil.getSampleTaTracker());
        assertEquals(taTrackerFromFile, typicalStudentsTaTracker);
    }

    @Test
    public void toModelType_duplicateModulesFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTaTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULES_FILE,
                JsonSerializableTaTracker.class).orElseThrow(NoSuchElementException::new);
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateSessionsFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTaTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_SESSIONS_FILE,
                JsonSerializableTaTracker.class).orElseThrow(NoSuchElementException::new);
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateDoneSessionsFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTaTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_DONE_SESSIONS_FILE,
                JsonSerializableTaTracker.class).orElseThrow(NoSuchElementException::new);
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
