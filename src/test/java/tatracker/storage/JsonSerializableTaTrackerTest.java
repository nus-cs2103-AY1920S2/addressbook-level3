package tatracker.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.TypicalStudents.getTypicalTaTracker;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.commons.util.JsonUtil;
import tatracker.model.TaTracker;

public class JsonSerializableTaTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTaTrackerTest");
    private static final Path TYPICAL_STUDENTS_FILE = TEST_DATA_FOLDER.resolve("typicalStudentsTaTracker.json");
    private static final Path INVALID_STUDENT_FILE = TEST_DATA_FOLDER.resolve("invalidStudentTaTracker.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentTaTracker.json");

    @Test
    public void toModelType_typicalStudentsFile_success() throws Exception {
        JsonSerializableTaTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDENTS_FILE,
                JsonSerializableTaTracker.class).get();
        TaTracker taTrackerFromFile = dataFromFile.toModelType();
        TaTracker typicalStudentsTaTracker = getTypicalTaTracker();
        assertEquals(taTrackerFromFile, typicalStudentsTaTracker);
    }

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTaTracker dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_FILE,
                JsonSerializableTaTracker.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
