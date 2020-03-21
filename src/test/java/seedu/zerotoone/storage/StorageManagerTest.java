package seedu.zerotoone.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.getTypicalExerciseList;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.zerotoone.commons.core.GuiSettings;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.storage.exercise.ExerciseListStorageManager;
import seedu.zerotoone.storage.userprefs.UserPrefsStorageManager;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        ExerciseListStorageManager exerciseListStorage = new ExerciseListStorageManager(
                getTempFilePath("exerciselist"));
        UserPrefsStorageManager userPrefsStorage = new UserPrefsStorageManager(getTempFilePath("prefs"));
        storageManager = new StorageManager(userPrefsStorage, exerciseListStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void exerciseListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonExerciseListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonExerciseListStorageTest} class.
         */
        ExerciseList original = getTypicalExerciseList();
        storageManager.saveExerciseList(original);
        ReadOnlyExerciseList retrieved = storageManager.readExerciseList().get();
        assertEquals(original, new ExerciseList(retrieved));
    }

    @Test
    public void getExerciseListFilePath() {
        assertNotNull(storageManager.getExerciseListFilePath());
    }

}
