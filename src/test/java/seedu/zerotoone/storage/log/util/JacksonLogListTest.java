package seedu.zerotoone.storage.log.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.commons.util.JsonUtil;
import seedu.zerotoone.model.log.LogList;
import seedu.zerotoone.testutil.log.TypicalLogs;

class JacksonLogListTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "log",
        "JacksonLogListTest");
    private static final Path TYPICAL_LOG_FILE = TEST_DATA_FOLDER.resolve("typicalLogList.json");
    private static final Path INVALID_LOG_FILE = TEST_DATA_FOLDER.resolve("invalidLogList.json");

    @Test
    public void toModelType_typicalWorkoutsFile_success() throws Exception {
        JacksonLogList dataFromFile = JsonUtil.readJsonFile(TYPICAL_LOG_FILE,
            JacksonLogList.class).get();
        LogList workoutListFromFile = dataFromFile.toModelType();
        LogList typicalWorkoutsWorkoutList = TypicalLogs.getTypicalLogList();
        assertEquals(workoutListFromFile.getLogList().size(), typicalWorkoutsWorkoutList.getLogList().size());
    }

    @Test
    public void toModelType_invalidWorkoutFile_throwsIllegalValueException() throws Exception {
        JacksonLogList dataFromFile = JsonUtil.readJsonFile(INVALID_LOG_FILE,
            JacksonLogList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
