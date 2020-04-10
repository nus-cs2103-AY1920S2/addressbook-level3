package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AssignmentSchedule;
import seedu.address.testutil.TypicalAssignments;

public class JsonSerializableAssignmentScheduleTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableAssignmentScheduleTest");
    private static final Path TYPICAL_ASSIGNMENTS_FILE =
            TEST_DATA_FOLDER.resolve("typicalAssignmentsScheduler.json");
    private static final Path INVALID_ASSIGNMENT_FILE =
            TEST_DATA_FOLDER.resolve("invalidAssignmentScheduler.json");
    private static final Path DUPLICATE_ASSIGNMENT_FILE =
            TEST_DATA_FOLDER.resolve("duplicateAssignmentScheduler.json");

    @Test
    public void toModelType_typicalAssignmentsFile_success() throws Exception {
        JsonSerializableAssignmentSchedule dataFromFile =
                JsonUtil.readJsonFile(TYPICAL_ASSIGNMENTS_FILE, JsonSerializableAssignmentSchedule.class).get();
        AssignmentSchedule assignmentScheduleFromFile = dataFromFile.toModelType();
        AssignmentSchedule typicalAssignmentsList = TypicalAssignments.getTypicalScheduler();
        assertEquals(assignmentScheduleFromFile, typicalAssignmentsList);
    }

    @Test
    public void toModelType_invalidAssignmentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAssignmentSchedule dataFromFile =
                JsonUtil.readJsonFile(INVALID_ASSIGNMENT_FILE, JsonSerializableAssignmentSchedule.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateAssignments_throwsIllegalValueException() throws Exception {
        JsonSerializableAssignmentSchedule dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ASSIGNMENT_FILE,
                JsonSerializableAssignmentSchedule.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAssignmentSchedule.MESSAGE_DUPLICATE_ASSIGNMENT,
                dataFromFile::toModelType);
    }
}
