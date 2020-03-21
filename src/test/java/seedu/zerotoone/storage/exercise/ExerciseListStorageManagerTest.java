package seedu.zerotoone.storage.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.BENCH_PRESS;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.OVERHEAD_PRESS;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.getTypicalExerciseList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;

public class ExerciseListStorageManagerTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "exercise",
            "ExerciseListStorageManagerTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readExerciseList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readExerciseList(null));
    }

    private java.util.Optional<ReadOnlyExerciseList> readExerciseList(String filePath) throws Exception {
        return new ExerciseListStorageManager(Paths.get(filePath))
                .readExerciseList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readExerciseList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readExerciseList("notJsonFormatExerciseList.json"));
    }

    @Test
    public void readExerciseList_invalidExerciseExerciseList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readExerciseList("invalidExerciseExerciseList.json"));
    }

    @Test
    public void readExerciseList_invalidAndValidExerciseExerciseList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readExerciseList("invalidAndValidExerciseExerciseList.json"));
    }

    @Test
    public void readAndSaveExerciseList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempExerciseList.json");
        ExerciseList original = getTypicalExerciseList();
        ExerciseListStorageManager jsonExerciseListStorage = new ExerciseListStorageManager(filePath);

        // Save in new file and read back
        jsonExerciseListStorage.saveExerciseList(original, filePath);
        ReadOnlyExerciseList readBack = jsonExerciseListStorage.readExerciseList(filePath).get();
        assertEquals(original, new ExerciseList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addExercise(OVERHEAD_PRESS);
        original.removeExercise(BENCH_PRESS);
        jsonExerciseListStorage.saveExerciseList(original, filePath);
        readBack = jsonExerciseListStorage.readExerciseList(filePath).get();
        assertEquals(original, new ExerciseList(readBack));

        // Save and read without specifying file path
        original.addExercise(BENCH_PRESS);
        jsonExerciseListStorage.saveExerciseList(original); // file path not specified
        readBack = jsonExerciseListStorage.readExerciseList().get(); // file path not specified
        assertEquals(original, new ExerciseList(readBack));

    }

    @Test
    public void saveExerciseList_nullExerciseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExerciseList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code exerciseList} at the specified {@code filePath}.
     */
    private void saveExerciseList(ReadOnlyExerciseList exerciseList, String filePath) {
        try {
            new ExerciseListStorageManager(Paths.get(filePath))
                    .saveExerciseList(exerciseList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveExerciseList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExerciseList(new ExerciseList(), null));
    }
}
