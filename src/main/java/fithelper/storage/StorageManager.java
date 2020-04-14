package fithelper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.DataConversionException;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.ReadOnlyUserPrefs;
import fithelper.model.ReadOnlyUserProfile;
import fithelper.model.ReadOnlyWeightRecords;
import fithelper.model.UserPrefs;

/**
 * Manages storage of FitHelper data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FitHelperStorage fitHelperStorage;
    private UserPrefsStorage userPrefsStorage;
    private UserProfileStorage userProfileStorage;
    private WeightRecordsStorage weightRecordsStorage;


    public StorageManager(FitHelperStorage fitHelperStorage, UserPrefsStorage userPrefsStorage,
                          UserProfileStorage userProfileStorage, WeightRecordsStorage weightRecordsStorage) {
        super();
        this.fitHelperStorage = fitHelperStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userProfileStorage = userProfileStorage;
        this.weightRecordsStorage = weightRecordsStorage;
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


    // ================ User Profile methods ==============================

    @Override
    public Path getUserProfilePath() {
        return userProfileStorage.getUserProfilePath();
    }

    @Override
    public Optional<ReadOnlyUserProfile> readUserProfile() throws DataConversionException, IOException {
        return readUserProfile(userProfileStorage.getUserProfilePath());
    }

    @Override
    public Optional<ReadOnlyUserProfile> readUserProfile(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read user profile data from file: " + filePath);
        return userProfileStorage.readUserProfile(filePath);
    }

    @Override
    public void saveUserProfile(ReadOnlyUserProfile userProfile) throws IOException {
        saveUserProfile(userProfile, userProfileStorage.getUserProfilePath());
    }

    @Override
    public void saveUserProfile(ReadOnlyUserProfile userProfile, Path filePath) throws IOException {
        logger.fine("Attempting to write to user profile data file: " + filePath);
        userProfileStorage.saveUserProfile(userProfile, filePath);
    }


    // ================ WeightRecords methods ==============================

    @Override
    public Path getWeightRecordsFilePath() {
        return weightRecordsStorage.getWeightRecordsFilePath();
    }

    @Override
    public Optional<ReadOnlyWeightRecords> readWeightRecords() throws DataConversionException, IOException {
        return readWeightRecords(weightRecordsStorage.getWeightRecordsFilePath());
    }

    @Override
    public Optional<ReadOnlyWeightRecords> readWeightRecords(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read weight data from file: " + filePath);
        return weightRecordsStorage.readWeightRecords(filePath);
    }

    @Override
    public void saveWeightRecords(ReadOnlyWeightRecords weightRecords) throws IOException {
        saveWeightRecords(weightRecords, weightRecordsStorage.getWeightRecordsFilePath());
    }

    @Override
    public void saveWeightRecords(ReadOnlyWeightRecords weightRecords, Path filePath) throws IOException {
        logger.fine("Attempting to write to weight data file: " + filePath);
        weightRecordsStorage.saveWeightRecords(weightRecords, filePath);
    }

}
