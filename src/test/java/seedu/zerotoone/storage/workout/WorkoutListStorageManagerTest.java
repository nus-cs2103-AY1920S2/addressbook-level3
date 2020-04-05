package seedu.zerotoone.storage.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.ARMS_WORKOUT;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.LEGS_WORKOUT;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.getTypicalWorkoutList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.WorkoutList;

public class WorkoutListStorageManagerTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "workout",
            "WorkoutListStorageManagerTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readWorkoutList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readWorkoutList(null));
    }

    private java.util.Optional<ReadOnlyWorkoutList> readWorkoutList(String filePath) throws Exception {
        return new WorkoutListStorageManager(Paths.get(filePath))
                .readWorkoutList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWorkoutList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readWorkoutList("notJsonFormatWorkoutList.json"));
    }

    @Test
    public void readWorkoutList_invalidWorkoutWorkoutList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readWorkoutList("invalidWorkoutWorkoutList.json"));
    }

    @Test
    public void readWorkoutList_invalidAndValidWorkoutWorkoutList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readWorkoutList("invalidAndValidWorkoutWorkoutList.json"));
    }

    @Test
    public void readAndSaveWorkoutList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempWorkoutList.json");
        WorkoutList original = getTypicalWorkoutList();
        WorkoutListStorageManager jsonWorkoutListStorage = new WorkoutListStorageManager(filePath);

        // Save in new file and read back
        jsonWorkoutListStorage.saveWorkoutList(original, filePath);
        ReadOnlyWorkoutList readBack = jsonWorkoutListStorage.readWorkoutList(filePath).get();
        assertEquals(original, new WorkoutList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addWorkout(LEGS_WORKOUT);
        original.removeWorkout(ARMS_WORKOUT);
        jsonWorkoutListStorage.saveWorkoutList(original, filePath);
        readBack = jsonWorkoutListStorage.readWorkoutList(filePath).get();
        assertEquals(original, new WorkoutList(readBack));

        // Save and read without specifying file path
        original.addWorkout(ARMS_WORKOUT);
        jsonWorkoutListStorage.saveWorkoutList(original); // file path not specified
        readBack = jsonWorkoutListStorage.readWorkoutList().get(); // file path not specified
        assertEquals(original, new WorkoutList(readBack));

    }

    @Test
    public void saveWorkoutList_nullWorkoutList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWorkoutList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code workoutList} at the specified {@code filePath}.
     */
    private void saveWorkoutList(ReadOnlyWorkoutList workoutList, String filePath) {
        try {
            new WorkoutListStorageManager(Paths.get(filePath))
                    .saveWorkoutList(workoutList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveWorkoutList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveWorkoutList(new WorkoutList(), null));
    }
}
