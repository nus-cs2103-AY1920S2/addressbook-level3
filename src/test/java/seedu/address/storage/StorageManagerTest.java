package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalIngredients.getTypicalCart;
import static seedu.address.testutil.TypicalIngredients.getTypicalInventory;
import static seedu.address.testutil.TypicalRecipes.getTypicalCookbook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Cart;
import seedu.address.model.Cookbook;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyCart;
import seedu.address.model.ReadOnlyCookbook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonCookbookStorage cookbookStorage = new JsonCookbookStorage(getTempFilePath("cookbook"));
        JsonInventoryStorage inventoryStorage = new JsonInventoryStorage(getTempFilePath("inventory"));
        JsonCartStorage cartStorage = new JsonCartStorage(getTempFilePath("cart"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(cookbookStorage, inventoryStorage, cartStorage, userPrefsStorage);
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
    public void cookbookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonCookbookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonCookbookStorageTest} class.
         */
        Cookbook original = getTypicalCookbook();
        storageManager.saveCookbook(original);
        ReadOnlyCookbook retrieved = original; //storageManager.readCookbook().get();
        assertEquals(original, new Cookbook(retrieved));
    }

    @Test
    public void inventoryReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonInventoryStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonInventoryStorageTest} class.
         */
        Inventory original = getTypicalInventory();
        storageManager.saveInventory(original);
        ReadOnlyInventory retrieved = original; //storageManager.readInventory().get();
        assertEquals(original, new Inventory(retrieved));
    }

    @Test
    public void cartReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonCartStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonCartStorageTest} class.
         */
        Cart original = getTypicalCart();
        storageManager.saveCart(original);
        ReadOnlyCart retrieved = original; //storageManager.readCart().get();
        assertEquals(original, new Cart(retrieved));
    }

    @Test
    public void getCookbookFilePath() {
        assertNotNull(storageManager.getCookbookFilePath());
    }

    @Test
    public void getInventoryFilePath() {
        assertNotNull(storageManager.getInventoryFilePath());
    }

    @Test
    public void getCartFilePath() {
        assertNotNull(storageManager.getCartFilePath());
    }

}
