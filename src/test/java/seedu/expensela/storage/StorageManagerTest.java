package seedu.expensela.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.expensela.commons.core.GuiSettings;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.ReadOnlyGlobalData;
import seedu.expensela.model.UserPrefs;


public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;
    private JsonExpenseLaStorage expenseLaStorage;
    private JsonUserPrefsStorage userPrefsStorage;
    private JsonGlobalDataStorage globalDataStorage;

    @BeforeEach
    public void setUp() {
        expenseLaStorage = new JsonExpenseLaStorage(getTempFilePath("el"));
        userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        globalDataStorage = new JsonGlobalDataStorage(getTempFilePath("gd"));
        storageManager = new StorageManager(expenseLaStorage, userPrefsStorage, globalDataStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void getUserPrefsFilePath_correctResult() {
        Path expectedPath = Paths.get(getTempFilePath("prefs").toString());
        assertEquals(userPrefsStorage.getUserPrefsFilePath(), expectedPath);
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

    @Test
    public void getGlobalDataFilePath_correctResult() {
        Path expectedPath = Paths.get(getTempFilePath("gd").toString());
        assertEquals(globalDataStorage.getGlobalDataFilePath(), expectedPath);
    }

    @Test
    public void globalDataReadWrite_sameObject() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonCurrencyMappingsStorage} class.
         */
        GlobalData original = new GlobalData();
        storageManager.saveGlobalData(original);
        ReadOnlyGlobalData retrieved = storageManager.readGlobalData().get();
        assertEquals(original, retrieved);
    }

}
