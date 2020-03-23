package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalOrders.getTypicalReturnOrderBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.returnorder.ReadOnlyReturnOrderBook;
import seedu.address.model.returnorder.ReturnOrderBook;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonOrderBookStorage deliveryOrderBookStorage = new JsonOrderBookStorage(getTempFilePath("ob"));
        JsonReturnOrderBookStorage returnOrderBookStorage = new JsonReturnOrderBookStorage(getTempFilePath("rob"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(deliveryOrderBookStorage, returnOrderBookStorage, userPrefsStorage);
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
    public void orderBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonReturnOrderBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonReturnOrderBookStorageTest} class.
         */
        OrderBook original = getTypicalOrderBook();
        storageManager.saveOrderBook(original);
        ReadOnlyOrderBook retrieved = storageManager.readOrderBook().get();
        assertEquals(original, new OrderBook(retrieved));
    }

    @Test
    public void returnOrderBookReadSave() throws Exception {
        ReturnOrderBook original = getTypicalReturnOrderBook();
        storageManager.saveReturnOrderBook(original);
        ReadOnlyReturnOrderBook retrieved = storageManager.readReturnOrderBook().get();
        assertEquals(original, new ReturnOrderBook(retrieved));
    }

    @Test
    public void getOrderBookFilePath() {
        assertNotNull(storageManager.getOrderBookFilePath());
    }

    @Test
    public void getReturnOrderBookFilePath() {
        assertNotNull(storageManager.getReturnOrderBookFilePath());
    }

}
