package seedu.expensela.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.expensela.commons.core.GuiSettings;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonExpenseLaStorage expenseLaStorage = new JsonExpenseLaStorage(getTempFilePath("el"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonGlobalDataStorage globalDataStorage = new JsonGlobalDataStorage(getTempFilePath("gd"));
        storageManager = new StorageManager(expenseLaStorage, userPrefsStorage, globalDataStorage);
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
    public void expenseLaReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonExpenseLaStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonExpenseLaStorageTest} class.
         */
        ExpenseLa original = getTypicalExpenseLa();
        storageManager.saveExpenseLa(original);
        ReadOnlyExpenseLa retrieved = storageManager.readExpenseLa().get();
        assertEquals(original, new ExpenseLa(retrieved));
    }

    @Test
    public void getExpenseLaFilePath() {
        assertNotNull(storageManager.getExpenseLaFilePath());
    }

}
