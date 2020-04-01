package seedu.recipe.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.recipe.commons.core.GuiSettings;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.storage.plan.JsonPlannedBookStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonRecipeBookStorage recipeBookStorage = new JsonRecipeBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonCookedRecordBookStorage recordBookStorage = new JsonCookedRecordBookStorage(getTempFilePath("rb"));
        JsonPlannedBookStorage plannedBookStorage = new JsonPlannedBookStorage(getTempFilePath("pb"));

        storageManager = new StorageManager(recipeBookStorage, recordBookStorage, plannedBookStorage, userPrefsStorage);
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
    public void recipeBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonRecipeBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonRecipeBookStorageTest} class.
         */
        RecipeBook original = getTypicalRecipeBook();
        storageManager.saveRecipeBook(original);
        ReadOnlyRecipeBook retrieved = storageManager.readRecipeBook().get();
        assertEquals(original, new RecipeBook(retrieved));
    }

    @Test
    public void getRecipeBookFilePath() {
        assertNotNull(storageManager.getRecipeBookFilePath());
    }

}
