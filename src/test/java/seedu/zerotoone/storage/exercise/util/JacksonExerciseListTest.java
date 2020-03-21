package seedu.zerotoone.storage.exercise.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.commons.util.JsonUtil;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.testutil.exercise.TypicalExercises;

public class JacksonExerciseListTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "exercise",
            "JacksonExerciseListTest");
    private static final Path TYPICAL_EXERCISES_FILE = TEST_DATA_FOLDER.resolve("typicalExercisesExerciseList.json");
    private static final Path INVALID_EXERCISE_FILE = TEST_DATA_FOLDER.resolve("invalidExerciseExerciseList.json");
    private static final Path DUPLICATE_EXERCISE_FILE = TEST_DATA_FOLDER.resolve("duplicateExerciseExerciseList.json");

    @Test
    public void toModelType_typicalExercisesFile_success() throws Exception {
        JacksonExerciseList dataFromFile = JsonUtil.readJsonFile(TYPICAL_EXERCISES_FILE,
                JacksonExerciseList.class).get();
        ExerciseList exerciseListFromFile = dataFromFile.toModelType();
        ExerciseList typicalExercisesExerciseList = TypicalExercises.getTypicalExerciseList();
        assertEquals(exerciseListFromFile, typicalExercisesExerciseList);
    }

    @Test
    public void toModelType_invalidExerciseFile_throwsIllegalValueException() throws Exception {
        JacksonExerciseList dataFromFile = JsonUtil.readJsonFile(INVALID_EXERCISE_FILE,
                JacksonExerciseList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateExercises_throwsIllegalValueException() throws Exception {
        JacksonExerciseList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EXERCISE_FILE,
                JacksonExerciseList.class).get();
        assertThrows(IllegalValueException.class, JacksonExerciseList.MESSAGE_DUPLICATE_EXERCISE,
                dataFromFile::toModelType);
    }

}
