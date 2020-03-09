package fithelper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.DataConversionException;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.ReadOnlyUserPrefs;
import fithelper.model.UserPrefs;

/**
 * Manages storage of FitHelper data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FitHelperStorage fitHelperStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(FitHelperStorage fitHelperStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.fitHelperStorage = fitHelperStorage;
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


    // ================ FitHelper methods ==============================

    @Override
    public Path getFitHelperFilePath() {
        return fitHelperStorage.getFitHelperFilePath();
    }

    @Override
    public Optional<ReadOnlyFitHelper> readFitHelper() throws DataConversionException, IOException {
        return readFitHelper(fitHelperStorage.getFitHelperFilePath());
    }

    @Override
    public Optional<ReadOnlyFitHelper> readFitHelper(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return fitHelperStorage.readFitHelper(filePath);
    }

    @Override
    public void saveFitHelper(ReadOnlyFitHelper fitHelper) throws IOException {
        saveFitHelper(fitHelper, fitHelperStorage.getFitHelperFilePath());
    }

    @Override
    public void saveFitHelper(ReadOnlyFitHelper fitHelper, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        fitHelperStorage.saveFitHelper(fitHelper, filePath);
    }

}
