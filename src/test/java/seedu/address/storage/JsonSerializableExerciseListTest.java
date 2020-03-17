package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ExerciseList;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableExerciseListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableExerciseListTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsExerciseList.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonExerciseList.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonExerciseList..json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableExerciseList dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableExerciseList.class).get();
        ExerciseList exerciseListFromFile = dataFromFile.toModelType();
        ExerciseList typicalPersonsExerciseList = TypicalPersons.getTypicalExerciseList();
        assertEquals(exerciseListFromFile, typicalPersonsExerciseList);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableExerciseList dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableExerciseList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableExerciseList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableExerciseList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableExerciseList.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
