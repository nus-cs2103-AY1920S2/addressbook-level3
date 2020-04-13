package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyModManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of ModManager data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ModManagerStorage modManagerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ModManagerStorage modManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.modManagerStorage = modManagerStorage;
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


    // ================ ModManager methods ==============================

    @Override
    public Path getModManagerFilePath() {
        return modManagerStorage.getModManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyModManager> readModManager() throws DataConversionException, IOException {
        return readModManager(modManagerStorage.getModManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyModManager> readModManager(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return modManagerStorage.readModManager(filePath);
    }

    @Override
    public void saveModManager(ReadOnlyModManager modManager) throws IOException {
        saveModManager(modManager, modManagerStorage.getModManagerFilePath());
    }

    @Override
    public void saveModManager(ReadOnlyModManager modManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        modManagerStorage.saveModManager(modManager, filePath);
    }

}
