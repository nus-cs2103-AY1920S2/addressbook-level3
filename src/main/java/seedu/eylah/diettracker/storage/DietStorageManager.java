package seedu.eylah.diettracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.commons.storage.UserPrefsStorage;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.ReadOnlyMyself;

/**
 * Manages storage of FoodBook data  and User metrics data in local storage.
 */
public class DietStorageManager implements DietStorage {

    private static final Logger logger = LogsCenter.getLogger(DietStorageManager.class);
    private UserPrefsStorage userPrefsStorage;
    private FoodBookStorage foodBookStorage;
    private MyselfStorage myselfStorage;

    public DietStorageManager(FoodBookStorage foodBookStorage, UserPrefsStorage userPrefsStorage,
                              MyselfStorage myselfStorage) {
        this.userPrefsStorage = userPrefsStorage;
        this.foodBookStorage = foodBookStorage;
        this.myselfStorage = myselfStorage;
    }

    // ================ Myself methods ================================
    @Override
    public Path getMyselfFilePath() {
        return myselfStorage.getMyselfFilePath();
    }

    @Override
    public Optional<ReadOnlyMyself> readMyself() throws DataConversionException, IOException {
        return readMyself(myselfStorage.getMyselfFilePath());
    }

    @Override
    public Optional<ReadOnlyMyself> readMyself(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return myselfStorage.readMyself(filePath);
    }

    @Override
    public void saveMyself(ReadOnlyMyself myself) throws IOException {
        logger.info(myself.toString());
        saveMyself(myself, myselfStorage.getMyselfFilePath());
    }

    @Override
    public void saveMyself(ReadOnlyMyself myself, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        myselfStorage.saveMyself(myself, filePath);
    }

    // ================ FoodBook methods ==============================

    @Override
    public Path getFoodBookFilePath() {
        return foodBookStorage.getFoodBookFilePath();
    }

    @Override
    public Optional<ReadOnlyFoodBook> readFoodBook() throws DataConversionException, IOException {
        return readFoodBook(foodBookStorage.getFoodBookFilePath());
    }

    @Override
    public Optional<ReadOnlyFoodBook> readFoodBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return foodBookStorage.readFoodBook(filePath);
    }

    @Override
    public void saveFoodBook(ReadOnlyFoodBook foodBook) throws IOException {
        saveFoodBook(foodBook, foodBookStorage.getFoodBookFilePath());
    }

    @Override
    public void saveFoodBook(ReadOnlyFoodBook foodBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        foodBookStorage.saveFoodBook(foodBook, filePath);
    }

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
}
