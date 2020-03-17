package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalExerciseList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ExerciseList;
import seedu.address.model.readOnlyExerciseList;

public class JsonExerciseListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonExerciseListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readExerciseList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readExerciseList(null));
    }

    private java.util.Optional<readOnlyExerciseList> readExerciseList(String filePath) throws Exception {
        return new JsonExerciseListStorage(Paths.get(filePath)).readExerciseList(addToTestDataPathIfNotNull(filePath));
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
    public void readExerciseList_invalidPersonExerciseList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readExerciseList("invalidPersonExerciseList.json"));
    }

    @Test
    public void readExerciseList_invalidAndValidPersonExerciseList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readExerciseList("invalidAndValidPersonExerciseList.json"));
    }

    @Test
    public void readAndSaveExerciseList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempExerciseList.json");
        ExerciseList original = getTypicalExerciseList();
        JsonExerciseListStorage jsonExerciseListStorage = new JsonExerciseListStorage(filePath);

        // Save in new file and read back
        jsonExerciseListStorage.saveExerciseList(original, filePath);
        readOnlyExerciseList readBack = jsonExerciseListStorage.readExerciseList(filePath).get();
        assertEquals(original, new ExerciseList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonExerciseListStorage.saveExerciseList(original, filePath);
        readBack = jsonExerciseListStorage.readExerciseList(filePath).get();
        assertEquals(original, new ExerciseList(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonExerciseListStorage.saveExerciseList(original); // file path not specified
        readBack = jsonExerciseListStorage.readExerciseList().get(); // file path not specified
        assertEquals(original, new ExerciseList(readBack));

    }

    @Test
    public void saveExerciseList_nullExerciseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveExerciseList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveExerciseList(readOnlyExerciseList exerciseList, String filePath) {
        try {
            new JsonExerciseListStorage(Paths.get(filePath))
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
