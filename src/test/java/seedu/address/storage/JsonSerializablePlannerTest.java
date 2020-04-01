package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Planner;
import seedu.address.testutil.TypicalModules;

public class JsonSerializablePlannerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePlannerTest");
    private static final Path TYPICAL_STUDENTS_FILE = TEST_DATA_FOLDER.resolve("typicalStudentPlanner.json");
    private static final Path INVALID_STUDENT_FILE = TEST_DATA_FOLDER.resolve("invalidStudentPlanner.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentPlanner.json");

    @Test
    public void toModelType_typicalStudentsFile_success() throws Exception {
        JsonSerializablePlanner dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDENTS_FILE,
            JsonSerializablePlanner.class).get();
        Planner plannerFromFile = dataFromFile.toModelType();
        Planner typicalStudentsPlanner = TypicalModules.getTypicalPlanner();
        assertEquals(plannerFromFile, typicalStudentsPlanner);
    }

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePlanner dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_FILE,
            JsonSerializablePlanner.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        JsonSerializablePlanner dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_FILE,
            JsonSerializablePlanner.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePlanner.MESSAGE_DUPLICATE_STUDENT,
            dataFromFile::toModelType);
    }

}
