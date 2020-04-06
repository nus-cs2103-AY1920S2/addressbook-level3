package seedu.zerotoone.storage.workout.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.commons.util.JsonUtil;
import seedu.zerotoone.model.workout.WorkoutList;
import seedu.zerotoone.testutil.workout.TypicalWorkouts;

public class JacksonWorkoutListTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "workout",
            "JacksonWorkoutListTest");
    private static final Path TYPICAL_WORKOUTS_FILE = TEST_DATA_FOLDER.resolve("typicalWorkoutsWorkoutList.json");
    private static final Path INVALID_WORKOUT_FILE = TEST_DATA_FOLDER.resolve("invalidWorkoutWorkoutList.json");
    private static final Path DUPLICATE_WORKOUT_FILE = TEST_DATA_FOLDER.resolve("duplicateWorkoutWorkoutList.json");

    @Test
    public void toModelType_typicalWorkoutsFile_success() throws Exception {
        JacksonWorkoutList dataFromFile = JsonUtil.readJsonFile(TYPICAL_WORKOUTS_FILE,
                JacksonWorkoutList.class).get();
        WorkoutList workoutListFromFile = dataFromFile.toModelType();
        WorkoutList typicalWorkoutsWorkoutList = TypicalWorkouts.getTypicalWorkoutList();
        assertEquals(workoutListFromFile, typicalWorkoutsWorkoutList);
    }

    @Test
    public void toModelType_invalidWorkoutFile_throwsIllegalValueException() throws Exception {
        JacksonWorkoutList dataFromFile = JsonUtil.readJsonFile(INVALID_WORKOUT_FILE,
                JacksonWorkoutList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateWorkouts_throwsIllegalValueException() throws Exception {
        JacksonWorkoutList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_WORKOUT_FILE,
                JacksonWorkoutList.class).get();
        assertThrows(IllegalValueException.class, JacksonWorkoutList.MESSAGE_DUPLICATE_WORKOUT,
                dataFromFile::toModelType);
    }

}
