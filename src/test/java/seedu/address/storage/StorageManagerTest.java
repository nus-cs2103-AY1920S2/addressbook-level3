package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUser.getTypicalUserData;
import static seedu.address.testutil.TypicalWallet.getTypicalWallet;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserData;
import seedu.address.model.ReadOnlyWallet;
import seedu.address.model.UserData;
import seedu.address.model.UserPrefs;
import seedu.address.model.Wallet;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonUserDataStorage userDataStorage = new JsonUserDataStorage(getTempFilePath("ud"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonWalletStorage walletStorage = new JsonWalletStorage(getTempFilePath("w"));
        storageManager = new StorageManager(addressBookStorage, userDataStorage, userPrefsStorage, walletStorage);
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
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void userDataReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserDataStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserDataStorageTest} class.
         */
        UserData original = getTypicalUserData();
        storageManager.saveUserData(original);
        ReadOnlyUserData retrieved = storageManager.readUserData().get();
        assertEquals(original, new UserData(retrieved));
    }

    @Test
    public void walletReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonWalletStorage} class.
         * More extensive testing of Wallet saving/reading is done in {@link JsonWalletStorageTest} class.
         */
        Wallet original = getTypicalWallet();
        storageManager.saveWallet(original);
        ReadOnlyWallet retrieved = storageManager.readWallet().get();
        assertEquals(original, new Wallet(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void getUserDataFilePath() {
        assertNotNull(storageManager.getUserDataFilePath());
    }

    @Test
    public void getWalletFilePath() {
        assertNotNull(storageManager.getWalletFilePath());
    }

}
