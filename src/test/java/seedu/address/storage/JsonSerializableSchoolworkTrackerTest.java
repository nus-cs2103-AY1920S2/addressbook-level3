package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.SchoolworkTracker;
import seedu.address.testutil.TypicalAssignments;

public class JsonSerializableSchoolworkTrackerTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableSchoolworkTrackerTest");
    private static final Path TYPICAL_ASSIGNMENTS_FILE =
            TEST_DATA_FOLDER.resolve("typicalSchoolworkTracker.json");
    private static final Path INVALID_ASSIGNMENT_FILE =
            TEST_DATA_FOLDER.resolve("invalidSchoolworkTracker.json");
    private static final Path DUPLICATE_ASSIGNMENT_FILE =
            TEST_DATA_FOLDER.resolve("duplicateSchoolworkTracker.json");

    @Test
    public void toModelType_typicalAssignmentsFile_success() throws Exception {
        JsonSerializableSchoolworkTracker dataFromFile =
                JsonUtil.readJsonFile(TYPICAL_ASSIGNMENTS_FILE, JsonSerializableSchoolworkTracker.class).get();
        SchoolworkTracker schoolworkTrackerFromFile = dataFromFile.toModelType();
        SchoolworkTracker typicalAssignmentsList = TypicalAssignments.getTypicalSchoolworkTracker();
        assertEquals(schoolworkTrackerFromFile, typicalAssignmentsList);
    }

    @Test
    public void toModelType_invalidAssignmentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableSchoolworkTracker dataFromFile =
                JsonUtil.readJsonFile(INVALID_ASSIGNMENT_FILE, JsonSerializableSchoolworkTracker.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateAssignments_throwsIllegalValueException() throws Exception {
        JsonSerializableSchoolworkTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ASSIGNMENT_FILE,
                JsonSerializableSchoolworkTracker.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableSchoolworkTracker.MESSAGE_DUPLICATE_ASSIGNMENT,
                dataFromFile::toModelType);
    }
}
