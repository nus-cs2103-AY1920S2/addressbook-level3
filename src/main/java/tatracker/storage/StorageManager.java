package tatracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tatracker.commons.core.LogsCenter;
import tatracker.commons.exceptions.DataConversionException;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.ReadOnlyUserPrefs;
import tatracker.model.UserPrefs;

/**
 * Manages storage of TaTracker data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaTrackerStorage taTrackerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TaTrackerStorage taTrackerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.taTrackerStorage = taTrackerStorage;
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


    // ================ TaTracker methods ==============================

    @Override
    public Path getTaTrackerFilePath() {
        return taTrackerStorage.getTaTrackerFilePath();
    }

    @Override
    public Optional<ReadOnlyTaTracker> readTaTracker() throws DataConversionException, IOException {
        return readTaTracker(taTrackerStorage.getTaTrackerFilePath());
    }

    @Override
    public Optional<ReadOnlyTaTracker> readTaTracker(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taTrackerStorage.readTaTracker(filePath);
    }

    @Override
    public void saveTaTracker(ReadOnlyTaTracker taTracker) throws IOException {
        saveTaTracker(taTracker, taTrackerStorage.getTaTrackerFilePath());
    }

    @Override
    public void saveTaTracker(ReadOnlyTaTracker taTracker, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taTrackerStorage.saveTaTracker(taTracker, filePath);
    }

}
