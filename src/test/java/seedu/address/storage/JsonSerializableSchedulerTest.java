package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Scheduler;
import seedu.address.testutil.TypicalAssignments;

public class JsonSerializableSchedulerTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableSchedulerTest");
    private static final Path TYPICAL_ASSIGNMENTS_FILE =
            TEST_DATA_FOLDER.resolve("typicalAssignmentsScheduler.json");
    private static final Path INVALID_ASSIGNMENT_FILE =
            TEST_DATA_FOLDER.resolve("invalidAssignmentScheduler.json");
    private static final Path DUPLICATE_ASSIGNMENT_FILE =
            TEST_DATA_FOLDER.resolve("duplicateAssignmentScheduler.json");

    @Test
    public void toModelType_typicalAssignmentsFile_success() throws Exception {
        JsonSerializableScheduler dataFromFile =
                JsonUtil.readJsonFile(TYPICAL_ASSIGNMENTS_FILE, JsonSerializableScheduler.class).get();
        Scheduler schedulerFromFile = dataFromFile.toModelType();
        Scheduler typicalAssignmentsList = TypicalAssignments.getTypicalScheduler();
        assertEquals(schedulerFromFile, typicalAssignmentsList);
    }

    @Test
    public void toModelType_invalidAssignmentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableScheduler dataFromFile =
                JsonUtil.readJsonFile(INVALID_ASSIGNMENT_FILE, JsonSerializableScheduler.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateAssignments_throwsIllegalValueException() throws Exception {
        JsonSerializableScheduler dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ASSIGNMENT_FILE,
                JsonSerializableScheduler.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableScheduler.MESSAGE_DUPLICATE_ASSIGNMENT,
                dataFromFile::toModelType);
    }
}
