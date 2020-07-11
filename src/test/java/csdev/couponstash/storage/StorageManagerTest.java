package csdev.couponstash.storage;

import static csdev.couponstash.testutil.TypicalCoupons.getTypicalCouponStash;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.ReadOnlyCouponStash;
import csdev.couponstash.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonCouponStashStorage addressBookStorage = new JsonCouponStashStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
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
    public void couponStashReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonCouponStashStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonCouponStashStorageTest} class.
         */
        CouponStash original = getTypicalCouponStash();
        storageManager.saveCouponStash(original);
        ReadOnlyCouponStash retrieved = storageManager.readCouponStash().get();
        assertEquals(original, new CouponStash(retrieved));
    }

    @Test
    public void getCouponStashFilePath() {
        assertNotNull(storageManager.getCouponStashFilePath());
    }

}
