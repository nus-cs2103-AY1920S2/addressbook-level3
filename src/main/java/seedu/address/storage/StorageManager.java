package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyInventorySystem;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of InventorySystem data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private InventorySystemStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(InventorySystemStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ InventorySystem methods ==============================

    @Override
    public Path getInventorySystemFilePath() {
        return addressBookStorage.getInventorySystemFilePath();
    }

    @Override
    public Optional<ReadOnlyInventorySystem> readInventorySystem() throws DataConversionException, IOException {
        return readInventorySystem(addressBookStorage.getInventorySystemFilePath());
    }

    @Override
    public Optional<ReadOnlyInventorySystem> readInventorySystem(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readInventorySystem(filePath);
    }

    @Override
    public void saveInventorySystem(ReadOnlyInventorySystem inventorySystem) throws IOException {
        saveInventorySystem(inventorySystem, addressBookStorage.getInventorySystemFilePath());
    }

    @Override
    public void saveInventorySystem(ReadOnlyInventorySystem addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveInventorySystem(addressBook, filePath);
    }

}
